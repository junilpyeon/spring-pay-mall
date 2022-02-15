package com.jpabook.jpashop.controller.v1;

import com.jpabook.jpashop.domain.member.User;
import com.jpabook.jpashop.exception.CEmailSigninFailedException;
import com.jpabook.jpashop.exception.CUserExistException;
import com.jpabook.jpashop.model.response.CommonResult;
import com.jpabook.jpashop.model.response.SingleResult;
import com.jpabook.jpashop.model.social.KakaoProfile;
import com.jpabook.jpashop.repository.UserJpaRepo;
import com.jpabook.jpashop.service.ResponseService;
import com.jpabook.jpashop.service.social.KakaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final UserJpaRepo userJpaRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;
    private final KakaoService kakaoService;
    private final Auth2Service Auth2Service;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public SingleResult<String> signin(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
                                       @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {

        User user = userJpaRepo.findByUid(id).orElseThrow(CEmailSigninFailedException::new);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new CEmailSigninFailedException();

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
    }

    @ApiOperation(value = "소셜 로그인", notes = "소셜 회원 로그인을 한다.")
    @PostMapping(value = "/signin/{provider}")
    public SingleResult<String> signinByProvider(
            @ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
            @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken) throws IOException, ParseException {
        
        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        String uniqueId = "";
        if(provider.equals("naver")) {
        	String Naverprofile = Auth2Service.getProfileFromNaver(accessToken);
            Map<String, Object> infoMap = new JSONParser(Naverprofile).parseObject();
            Map<String, Object> infoResp = (Map<String, Object>) infoMap.get("response");
            uniqueId = infoResp.get("id").toString();
        }
        
        
        User user = userJpaRepo.findByUidAndProvider(String.valueOf(profile.getId()), provider).orElseThrow(CUserNotFoundException::new);
        if(provider.equals("naver")) {
        user = userJpaRepo.findByUidAndProvider(uniqueId, provider).orElseThrow(CUserNotFoundException::new);
        }
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signup(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
                               @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                               @ApiParam(value = "닉네임", required = true) @RequestParam String name) {

        userJpaRepo.save(User.builder()
                .uid(id)
                .password(passwordEncoder.encode(password))
                .nicname(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "소셜 계정 가입", notes = "소셜 계정 회원가입을 한다.")
    @PostMapping(value = "/signup/{provider}")
    public CommonResult signupProvider(@ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
                                       @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken,
                                       @ApiParam(value = "닉네임", required = true) @RequestParam String nicname, @ApiParam(value = "성별") @RequestParam String gender, @ApiParam(value = "사이즈") @RequestParam String clothes_size) throws ParseException, IOException {

        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        
        String uniqueId = "";
        if(provider.equals("naver")) {
        	String Naverprofile = Auth2Service.getProfileFromNaver(accessToken);
            Map<String, Object> infoMap = new JSONParser(Naverprofile).parseObject();
            Map<String, Object> infoResp = (Map<String, Object>) infoMap.get("response");
            uniqueId = infoResp.get("id").toString();
        }
        
        Optional<User> user = userJpaRepo.findByUidAndProvider(String.valueOf(profile.getId()), provider);
        if (user.isPresent())
            throw new CUserExistException();

        
        User inUser = new User();
        if(provider.equals("naver")) {
        inUser = User.builder()
        		.uid(uniqueId)
                .provider(provider)
                .nicname(nicname)
                .gender(gender)
                .clothes_size(clothes_size)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        }else {
        inUser = User.builder()
                .uid(String.valueOf(profile.getId()))
                .provider(provider)
                .nicname(nicname)
                .gender(gender)
                .clothes_size(clothes_size)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        }

        userJpaRepo.save(inUser);
        return responseService.getSuccessResult();
    }
    
    
}
