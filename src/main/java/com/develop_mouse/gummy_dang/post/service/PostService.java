package com.develop_mouse.gummy_dang.post.service;

import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.post.DTO.PostDTO;

import java.util.List;

public interface PostService {

    Response<Void> createPost(PostDTO postDTO);
    Response<Void> updatePost(Long id, PostDTO postDTO);
    Response<Void> deletePost(Long id);
    Response<PostDTO> detailPost(Long id);
    Response<List<PostDTO>> postList();

}
