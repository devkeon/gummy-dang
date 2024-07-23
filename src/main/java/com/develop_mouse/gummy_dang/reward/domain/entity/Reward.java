package com.develop_mouse.gummy_dang.reward.domain.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.develop_mouse.gummy_dang.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@SQLDelete(sql = "UPDATE reward SET active_status = 'DELETED' WHERE reward_id = ?")
@SQLRestriction("active_status <> 'DELETED'")
public class Reward extends BaseEntity {

	@Id @Column(name = "reward_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
}
