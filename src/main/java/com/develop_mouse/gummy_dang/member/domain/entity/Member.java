package com.develop_mouse.gummy_dang.member.domain.entity;

import java.util.Set;

import com.develop_mouse.gummy_dang.common.entity.BaseEntity;
import com.develop_mouse.gummy_dang.like.domain.entity.Like;
import com.develop_mouse.gummy_dang.post.domain.entity.Post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id @Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "member")
	private Set<Post> posts;

	@OneToMany(mappedBy = "member")
	private Set<Like> like;

	@NotNull
	@Column(length = 20)
	private String userName;
	@NotNull
	private String password;

	private String address;
	@Column(length = 20)
	private String phoneNumber;
	@Column(length = 20)
	private String socialId;

}
