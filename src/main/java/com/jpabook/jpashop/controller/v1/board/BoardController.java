package com.jpabook.jpashop.controller.v1.board;

import com.rest.api.entity.board.Board;
import com.rest.api.entity.board.Post;
import com.rest.api.model.board.ParamsPost;
import com.rest.api.model.response.CommonResult;
import com.rest.api.model.response.ListResult;
import com.rest.api.model.response.SingleResult;
import com.rest.api.service.ResponseService;
import com.rest.api.service.board.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Api(tags = {"3. Board"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/board")
public class BoardController {

    private final BoardService boardService;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "�α��� ���� �� access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "�Խ��� ����", notes = "�ű� �Խ����� �����Ѵ�.")
    @PostMapping(value = "/{boardName}")
    public SingleResult<Board> createBoard(@PathVariable String boardName) {
        return responseService.getSingleResult(boardService.insertBoard(boardName));
    }

    @ApiOperation(value = "�Խ��� ���� ��ȸ", notes = "�Խ��� ������ ��ȸ�Ѵ�.")
    @GetMapping(value = "/{boardName}")
    public SingleResult<Board> boardInfo(@PathVariable String boardName) {
        return responseService.getSingleResult(boardService.findBoard(boardName));
    }

    @ApiOperation(value = "�Խñ� ����Ʈ", notes = "�Խñ� ����Ʈ�� ��ȸ�Ѵ�.")
    @GetMapping(value = "/{boardName}/posts")
    public ListResult<Post> posts(@PathVariable String boardName, String gubun) {
        return responseService.getListResult(boardService.findPosts(boardName, gubun));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "�α��� ���� �� access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "�Խñ� �ۼ�", notes = "�Խñ��� �ۼ��Ѵ�.")
    @PostMapping(value = "/{boardName}/feed")
    public SingleResult<Post> post(@PathVariable String boardName, @RequestParam("img") MultipartFile files, @Valid @ModelAttribute ParamsPost post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();
        //���� ���ε�
        String baseDir = "C:\\Users\\junveom\\git\\SpringRestApi\\Documents\\ServerFiles";
        String filePath = baseDir + "\\" + files.getOriginalFilename();
        try {
			files.transferTo(new File(filePath));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return responseService.getSingleResult(boardService.writePost(uid, boardName, post));
    }

    @ApiOperation(value = "�Խñ� ��", notes = "�Խñ� �������� ��ȸ�Ѵ�.")
    @GetMapping(value = "/post/{postId}")
    public SingleResult<Post> post(@PathVariable long postId) {
        return responseService.getSingleResult(boardService.getPost(postId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "�α��� ���� �� access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "�Խñ� ����", notes = "�Խ����� ���� �����Ѵ�.")
    @PutMapping(value = "/feed/{feedId}")
    public SingleResult<Post> post(@PathVariable long postId, @RequestParam("img") MultipartFile files, @Valid @ModelAttribute ParamsPost post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();
        return responseService.getSingleResult(boardService.updatePost(postId, uid, post));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "�α��� ���� �� access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "�Խñ� ����", notes = "�Խñ��� �����Ѵ�.")
    @DeleteMapping(value = "/feed/{feedId}")
    public CommonResult deletePost(@PathVariable long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();
        boardService.deletePost(postId, uid);
        return responseService.getSuccessResult();
    }
}
