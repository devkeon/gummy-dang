package com.develop_mouse.gummy_dang.reward.domain.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.develop_mouse.gummy_dang.common.domain.entity.BaseEntity;
import com.develop_mouse.gummy_dang.member.domain.entity.Member;
import com.develop_mouse.gummy_dang.reward.domain.RewardStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	private Double percentage;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@Enumerated(value = EnumType.STRING)
	private RewardStatus rewardStatus;

	public void updateRewardStatus(RewardStatus rewardStatus) {
		this.rewardStatus = rewardStatus;
	}

	public void updatePercentage(Double percentage) {
		this.percentage = percentage;
	}
}
