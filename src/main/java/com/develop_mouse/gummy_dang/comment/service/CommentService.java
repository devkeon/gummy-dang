package com.develop_mouse.gummy_dang.comment.service;

import com.develop_mouse.gummy_dang.comment.domain.request.CommentRequest;
import com.develop_mouse.gummy_dang.comment.domain.response.CommentResponse;
import com.develop_mouse.gummy_dang.common.domain.response.Response;

import java.util.List;

public interface CommentService {

    Response<CommentResponse> createComment(CommentRequest commentRequest);
    Response<CommentResponse> updateComment(CommentRequest commentRequest);
    // Response<CommentResponse> detailComment(Long id);
    Response<Void> deleteComment(CommentRequest commentRequest);
    Response<List<CommentResponse>> listComments(Long postId);

}
