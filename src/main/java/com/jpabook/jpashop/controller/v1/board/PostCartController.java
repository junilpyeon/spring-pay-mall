package com.jpabook.jpashop.controller.v1.board;

import com.rest.api.entity.board.PostCart;
import com.rest.api.service.board.PostCartService;
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

@Api(tags = {"4. PostCart"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/postcart")
public class PostCartController {
   private final PostCartService PostCartService;

   @ApiImplicitParams({
       @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "�α��� ���� �� access_token", required = true, dataType = "String", paramType = "header")
   })
   @ApiOperation(value = "�ǵ� ���ƿ� ��ü��ȸ", notes = "�ǵ� ���ƿ� ��ü��ȸ")
   @GetMapping
   public List<PostCart> selectPostCarts(@PathVariable long groupid) {
	   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String id = authentication.getName();
      return PostCartService.selectPostCarts(id, groupid);
   }

   @ApiImplicitParams({
       @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "�α��� ���� �� access_token", required = true, dataType = "String", paramType = "header")
   })
   @ApiOperation(value = "�ǵ� ���ƿ� 1�� ��ȸ", notes = "�ǵ� ���ƿ� 1�� ��ȸ")
   @GetMapping( value = "/{id}")
   public PostCart getPostCart(@PathVariable Long feed_id) {
	   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String id = authentication.getName();
      return PostCartService.getPostCart(feed_id, id);
   }

   @ApiImplicitParams({
       @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "�α��� ���� �� access_token", required = true, dataType = "String", paramType = "header")
   })
   @ApiOperation(value = "�ǵ� ���ƿ� ����", notes = "�ǵ� ���ƿ� ����")
   @PostMapping
   public PostCart savePostCart(@RequestBody PostCart PostCart) {
      return PostCartService.savePostCart(PostCart);
   }

   @ApiImplicitParams({
       @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "�α��� ���� �� access_token", required = true, dataType = "String", paramType = "header")
   })
   @ApiOperation(value = "�ǵ� ���ƿ� ����", notes = "�ǵ� ���ƿ� ����")
   @PutMapping("/{id}")
   public PostCart updatePostCart(@PathVariable Long feedlike_id, @RequestBody PostCart PostCart) {
      return PostCartService.updatePostCart(feedlike_id, PostCart);
   }

   @ApiImplicitParams({
       @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "�α��� ���� �� access_token", required = true, dataType = "String", paramType = "header")
   })
   @ApiOperation(value = "�ǵ� ���ƿ� ����", notes = "�ǵ� ���ƿ� ����")
   @DeleteMapping
   @ResponseStatus(HttpStatus.OK)
   public void deletePostCart(@PathVariable Long feedlike_id) {
	   PostCartService.deletePostCart(feedlike_id);
   }
}
