package com.develop_mouse.gummy_dang.post.service;

import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.post.DTO.PostDTO;
import com.develop_mouse.gummy_dang.post.domain.request.PostRequest;
import com.develop_mouse.gummy_dang.post.domain.response.PostResponse;

import java.util.List;

public interface PostService {

    Response<PostResponse> createPost(PostRequest postRequest);
    Response<PostResponse> updatePost(PostRequest postRequest);
    Response<Void> deletePost(Long id);
    Response<PostResponse> detailPost(Long id);
    Response<List<PostResponse>> postList();

}
