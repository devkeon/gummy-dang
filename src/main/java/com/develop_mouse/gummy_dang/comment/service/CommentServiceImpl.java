package com.develop_mouse.gummy_dang.comment.service;

import com.develop_mouse.gummy_dang.authentication.util.SecurityContextUtil;
import com.develop_mouse.gummy_dang.comment.domain.entity.Comment;
import com.develop_mouse.gummy_dang.comment.domain.request.CommentRequest;
import com.develop_mouse.gummy_dang.comment.domain.response.CommentResponse;
import com.develop_mouse.gummy_dang.comment.repository.CommentRepository;
import com.develop_mouse.gummy_dang.common.Exception.BusinessException;
import com.develop_mouse.gummy_dang.common.domain.ResponseCode;
import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.member.domain.entity.Member;
import com.develop_mouse.gummy_dang.member.repository.MemberRepository;
import com.develop_mouse.gummy_dang.post.domain.entity.Post;
import com.develop_mouse.gummy_dang.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final SecurityContextUtil securityContextUtil;

    @Override
    public Response<CommentResponse> createComment(CommentRequest commentRequest) {

        Member user = memberRepository.findById(securityContextUtil.getContextMemberInfo().getMemberId())
                .stream()
                .findAny()
                .orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(commentRequest.getPostId())
            .stream()
            .findAny()
            .orElseThrow(() -> new BusinessException(ResponseCode.POST_NOT_FOUND));

        Comment comment = Comment.builder()
                .member(user)
                .post(post)
                .content(commentRequest.getContent())
                .build();

        Comment savedComment = commentRepository.save(comment);

        CommentResponse response = CommentResponse.builder()
            .id(savedComment.getId())
            .postId(savedComment.getPost().getId())
            .content(savedComment.getContent())
            .createdAt(LocalDate.from(savedComment.getCreatedAt()))
            .build();


        return Response.ok(response);
    }

    @Override
    public Response<CommentResponse> updateComment(CommentRequest commentRequest) {

        Comment comment = commentRepository.findById(commentRequest.getId())
            .stream()
            .findAny()
            .orElseThrow(() -> new BusinessException(ResponseCode.COMMENT_NOT_FOUND));

        Member user = memberRepository.findById(securityContextUtil.getContextMemberInfo().getMemberId()).stream()
            .findAny()
            .orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

        if (!comment.getMember().equals(user)){
            throw new BusinessException(ResponseCode.COMMENT_WRITER_DIFFERENCE);
        }

        comment.updateContent(commentRequest.getContent());

        Comment savedComment = commentRepository.save(comment);

        CommentResponse response = CommentResponse.builder()
            .id(savedComment.getId())
            .postId(savedComment.getPost().getId())
            .content(savedComment.getContent())
            .createdAt(LocalDate.from(savedComment.getCreatedAt()))
            .build();

        return Response.ok(response);
    }

    @Override
    public Response<Void> deleteComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId).stream()
                .findAny()
                .orElseThrow(()-> new BusinessException(ResponseCode.COMMENT_NOT_FOUND));

        Member user = memberRepository.findById(securityContextUtil.getContextMemberInfo().getMemberId()).stream()
                .findAny()
                .orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

        if (!comment.getMember().equals(user)){
            throw new BusinessException(ResponseCode.COMMENT_WRITER_DIFFERENCE);
        }

        commentRepository.deleteById(comment.getId());

        return Response.ok();
    }

    @Override
    public Response<List<CommentResponse>> listComments(Long postId) {

        Post post = postRepository.findById(postId)
            .stream()
            .findAny()
            .orElseThrow(() -> new BusinessException(ResponseCode.POST_NOT_FOUND));

        List<Comment> comments = commentRepository.findByPost(post);

        List<CommentResponse> commentResponses = comments.stream()
            .map(comment -> CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .content(comment.getContent())
                .createdAt(LocalDate.from(comment.getCreatedAt()))
                .build())
            .toList();

        return Response.ok(commentResponses);
    }
}
