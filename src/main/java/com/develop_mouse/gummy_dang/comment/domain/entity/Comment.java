package com.develop_mouse.gummy_dang.comment.domain.entity;

import com.develop_mouse.gummy_dang.member.domain.entity.Member;
import com.develop_mouse.gummy_dang.post.domain.entity.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
//@SQLDelete(sql = "UPDATE comments SET active_status = 'DELETED' WHERE comment_id = ?")
//@SQLRestriction("active_status <> 'DELETED'")
//요 column이 DB에 없다고 계속 오류나서 일단 주석처리 했어염 !!
public class Comment {
    @Getter
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @NotNull
    @ManyToOne
    @JoinColumn(name = "member")
    private Member member;

    @Getter
    @NotNull
    @ManyToOne
    @JoinColumn(name = "post")
    private Post post;

    @NotNull
    @Column(length = 200)
    private String content;

    public void updateContent(@NotNull String content) {
        this.content = content;
    }
}
