package com.develop_mouse.gummy_dang.member.domain.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.develop_mouse.gummy_dang.common.domain.entity.BaseEntity;
import com.develop_mouse.gummy_dang.like.domain.entity.Like;
import com.develop_mouse.gummy_dang.member.domain.Role;
import com.develop_mouse.gummy_dang.post.domain.entity.Post;
import com.develop_mouse.gummy_dang.walkrecord.domain.entity.WalkRecord;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(
	uniqueConstraints = {
		@UniqueConstraint(
			name = "user_name_unique",
			columnNames = {"user_name"}
		)
	}
)
@SQLDelete(sql = "UPDATE member SET active_status = 'DELETED' WHERE member_id = ?")
@SQLRestriction("active_status <> 'DELETED'")
public class Member extends BaseEntity {

	@Id @Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Post> posts;

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Like> like;

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<WalkRecord> walkRecords;

	@NotNull
	@Column(length = 20)
	private String userName;
	@NotNull
	private String password;
	@NotNull
	@Column(length = 20)
	private String nickname;

	private String refreshToken;

	private String address;
	@Column(length = 20)
	private String phoneNumber;

	private Long socialId;

	@NotNull
	@Enumerated(value = EnumType.STRING)
	private Role role;

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
