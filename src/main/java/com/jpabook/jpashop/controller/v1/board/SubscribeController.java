package com.jpabook.jpashop.controller.v1.board;

import com.rest.api.entity.board.Subscribe;
import com.rest.api.service.board.SubscribeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"5. Subscribe"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/subscribe")
public class SubscribeController {
   private final SubscribeService SubscribeService;

   @ApiImplicitParams({
       @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
   })
   @ApiOperation(value = "구독 정보 전체조회", notes = "구독 정보 전체조회")
   @GetMapping
   public List<Subscribe> selectSubscribes(@PathVariable String subs_group_id) {
	   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String id = authentication.getName();
       
      return SubscribeService.selectSubscribes(id, subs_group_id);
   }

   @ApiImplicitParams({
       @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
   })
   @ApiOperation(value = "구독 정보 1건 조회", notes = "구독 정보 1건 조회")
   @GetMapping( value = "/{id}")
   public Subscribe getSubscribe(@PathVariable String subs_uid) {
	   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String id = authentication.getName();
	   
      return SubscribeService.getSubscribe(id, subs_uid);
   }

   @ApiImplicitParams({
       @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
   })
   @ApiOperation(value = "구독 정보 저장", notes = "구독 정보 저장")
   @PostMapping
   public Subscribe saveSubscribe(@RequestBody Subscribe Subscribe) {
      return SubscribeService.saveSubscribe(Subscribe);
   }

   @ApiImplicitParams({
       @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
   })
   @ApiOperation(value = "구독 정보 수정", notes = "구독 정보 수정")
   @PutMapping("/{id}")
   public Subscribe updateSubscribe(@PathVariable String id, String userid,@RequestBody Subscribe Subscribe) {
      return SubscribeService.updateSubscribe(id, userid, Subscribe);
   }

   @ApiImplicitParams({
       @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
   })
   @ApiOperation(value = "구독 정보 삭제", notes = "구독 정보 삭제")
   @DeleteMapping
   @ResponseStatus(HttpStatus.OK)
   public void deleteSubscribe(@PathVariable String id, String userid) {
	   SubscribeService.deleteSubscribe(id, userid);
   }
}
