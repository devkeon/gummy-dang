package com.develop_mouse.gummy_dang.post.domain.entity;

import com.develop_mouse.gummy_dang.common.domain.entity.BaseEntity;
import com.develop_mouse.gummy_dang.like.domain.entity.Like;
import com.develop_mouse.gummy_dang.member.domain.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE post SET active_status = 'DELETED' WHERE post_id = ?")
@SQLRestriction("active_status <> 'DELETED'")

public class Post extends BaseEntity {

	@Getter
	@Id @Column(name = "post_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	@NotNull
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<PostCoordinate> postCoordinates;

	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<PostImage> postImages;

	@OneToMany(mappedBy = "post")
	private Set<Like> like;

	// 게시물 제목/좋아요 수/내용
	@NotNull
	@Column(length = 50)
	private String title;
	private Integer likeCount;
	private String description;

	// +) 이미지 관련해서 추가한 부분
	private String imageUrl;

	public void updateTitle(@NotNull String title) {
		this.title = title;
	}

	public void updateDescription(String description) {
		this.description = description;
	}

	public void addCoordinate(PostCoordinate coordinate){
		if (this.postCoordinates == null) {
			this.postCoordinates = new HashSet<>();
		}
		this.postCoordinates.add(coordinate);
		coordinate.updatePost(this);
	}
}
