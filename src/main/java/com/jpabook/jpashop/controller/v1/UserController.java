package com.jpabook.jpashop.controller.v1;

import com.jpabook.jpashop.domain.member.User;
import com.jpabook.jpashop.model.response.CommonResult;
import com.jpabook.jpashop.model.response.SingleResult;
import com.jpabook.jpashop.repository.UserJpaRepo;
import com.jpabook.jpashop.service.ResponseService;
import com.jpabook.jpashop.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {

	private final UserService UserService;
    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService; // ����� ó���� Service

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "�α��� ���� �� access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "ȸ�� ����Ʈ ��ȸ", notes = "��� ȸ���� ��ȸ�Ѵ�")
    @GetMapping(value = "/users")
    public User findAllUser(@ApiParam(value = "����") @RequestParam String gender, @ApiParam(value = "������") @RequestParam String clothes_size) {
        // ��������Ͱ� �������ΰ�� getListResult�� �̿��ؼ� ����� ����Ѵ�.
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        
        return UserService.getUserList(id, gender, clothes_size);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "�α��� ���� �� access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "ȸ�� �ܰ� ��ȸ", notes = "ȸ����ȣ(msrl)�� ȸ���� ��ȸ�Ѵ�")
    @GetMapping(value = "/user")
    public User findUser(@ApiParam(value = "�ٸ�����id") @RequestParam String uid) {
        // SecurityContext���� �������� ȸ���� ������ ���´�.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        // ��������Ͱ� ���ϰ��ΰ�� getSingleResult�� �̿��ؼ� ����� ����Ѵ�.
        return UserService.getUserInfo(id, uid);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "�α��� ���� �� access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "ȸ�� ����", notes = "ȸ�������� �����Ѵ�")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "ȸ����ȣ", required = true) @RequestParam long msrl,
            @ApiParam(value = "ȸ���̸�", required = true) @RequestParam String nicname, @ApiParam(value = "����") @RequestParam String gender, @ApiParam(value = "������") @RequestParam String clothes_size) {
        User user = User.builder()
                .msrl(msrl)
                .nicname(nicname)
                .gender(gender)
                .clothes_size(clothes_size)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "�α��� ���� �� access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "ȸ�� ����", notes = "ȸ����ȣ(msrl)�� ȸ�������� �����Ѵ�")
    @DeleteMapping(value = "/user/{msrl}")
    public CommonResult delete(
            @ApiParam(value = "ȸ����ȣ", required = true) @PathVariable long msrl) {
        userJpaRepo.deleteById(msrl);
        // ���� ��� ������ �ʿ��Ѱ�� getSuccessResult()�� �̿��Ͽ� ����� ����Ѵ�.
        return responseService.getSuccessResult();
    }
}


