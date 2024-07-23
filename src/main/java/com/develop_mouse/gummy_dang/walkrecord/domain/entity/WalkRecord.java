package com.develop_mouse.gummy_dang.walkrecord.domain.entity;

import java.time.LocalDate;

import com.develop_mouse.gummy_dang.common.entity.BaseEntity;
import com.develop_mouse.gummy_dang.member.domain.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class WalkRecord extends BaseEntity {

	@Id
	@Column(name = "walk_record_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "gummy_id")
	private Gummy gummy;

	@Column(length = 50)
	@NotNull
	private String departure;

	@Column(length = 50)
	@NotNull
	private String arrival;

	@NotNull
	private LocalDate recordDate;

}
