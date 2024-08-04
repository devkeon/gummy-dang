package com.develop_mouse.gummy_dang.post.controller;

import com.develop_mouse.gummy_dang.common.domain.response.Response;

import com.develop_mouse.gummy_dang.post.service.PostService;
import com.develop_mouse.gummy_dang.post.DTO.PostDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    게시판 주요 기능
    1) 글목록 (/post/list)
    2) 작성 (/post/create)
        - 지도 좌표값 받아와야 ..!
    3) 조회 (/post/{id})
    4) 수정 (/post/update/{id})
    5) 삭제 (/post/delete/{id})

*/


@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 글 목록
    @GetMapping("/post/list")
    public Response<List<PostDTO>> postList(){
        return postService.postList();
    }

    // 글 작성
    @PostMapping("/post/create")
    public Response<Void> createPost(@RequestBody PostDTO postDTO){
        postService.createPost(postDTO);
        return Response.ok();
    }

    // 글 수정
    @PostMapping("/post/update/{id}")
    public Response<Void> updatePost(@PathVariable long id, @RequestBody PostDTO postDTO){
        postService.updatePost(id, postDTO);
        return Response.ok();
    }

    // 특정 id의 게시글 삭제
    @DeleteMapping("/post/delete/{id}")
    public Response<Void> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return Response.ok();
    }

    // 글 조회
    @GetMapping("/post/{id}")
    public Response<PostDTO> detailPost(@PathVariable Long id) {
        return postService.detailPost(id);
    }

}
