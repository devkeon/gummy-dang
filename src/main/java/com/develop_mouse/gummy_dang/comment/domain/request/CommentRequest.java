package com.develop_mouse.gummy_dang.comment.domain.request;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentRequest { // 받아오는

    private Long id; // comment_id!!
    private Long postId;
    private String content;

}
