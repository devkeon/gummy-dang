package com.develop_mouse.gummy_dang.comment.domain.response;

import lombok.*;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponse { // 보내는

    private Long id; // comment_id!
    private Long postId;
    private String content;
    private LocalDate createdAt;

}
