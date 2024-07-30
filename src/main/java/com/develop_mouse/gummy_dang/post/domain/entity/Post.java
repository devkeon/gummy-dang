package com.develop_mouse.gummy_dang.post.domain.entity;

import java.util.Set;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.develop_mouse.gummy_dang.common.domain.entity.BaseEntity;
import com.develop_mouse.gummy_dang.like.domain.entity.Like;
import com.develop_mouse.gummy_dang.member.domain.entity.Member;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//+)
import java.time.LocalDateTime;

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

 	// +) 날짜 관련해서 추가한 부분
	private LocalDateTime createdAt; // 생성 시간
	private LocalDateTime updatedAt; // 수정 시간


	// +) 이미지 관련해서 추가한 부분
	private String imageUrl;
	public String getImageUrl() {
		return imageUrl;
	}

	public Long getId() {
		return id;
	}

	public Member getMember() {
		return member;
	}

	public Set<PostCoordinate> getPostCoordinates() { return postCoordinates; }

	//public Set<PostImage> getPostImages() {	return postImages;	}

	//public Set<Like> getLike() {	return like;  }

	public String getTitle() {
		return title;
	}

	//public Integer getLikeCount() {return likeCount;}

	public String getDescription() {
		return description;
	}



}
