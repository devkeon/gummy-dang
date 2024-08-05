package com.develop_mouse.gummy_dang.post.controller;

import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.post.DTO.PostDTO;
import com.develop_mouse.gummy_dang.post.domain.request.PostRequest;
import com.develop_mouse.gummy_dang.post.domain.response.PostResponse;
import com.develop_mouse.gummy_dang.post.service.PostService;
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
    public Response<List<PostResponse>> postList(){
        return postService.postList();
    }

    // 글 작성
    @PostMapping("/post")
    public Response<PostResponse> createPost(@RequestBody PostRequest postRequest){
        return postService.createPost(postRequest);
    }

    // 글 수정
    @PatchMapping("/post")
    public Response<PostResponse> updatePost(@RequestBody PostRequest postRequest){
        return postService.updatePost(postRequest);
    }

    // 특정 id의 게시글 삭제
    @DeleteMapping("/post/{id}")
    public Response<Void> deletePost(@PathVariable Long id){
        return postService.deletePost(id);
    }

    // 글 조회
    @GetMapping("/post/{id}")
    public Response<PostResponse> detailPost(@PathVariable Long id) {
        return postService.detailPost(id);
    }

}
