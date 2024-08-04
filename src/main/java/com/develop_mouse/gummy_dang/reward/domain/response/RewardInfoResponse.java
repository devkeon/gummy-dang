package com.develop_mouse.gummy_dang.reward.domain.response;

import com.develop_mouse.gummy_dang.reward.domain.RewardStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RewardInfoResponse {

	private Double percentage;
	private RewardStatus rewardStatus;

}
