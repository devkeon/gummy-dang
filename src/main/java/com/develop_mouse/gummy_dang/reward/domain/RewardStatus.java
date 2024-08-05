package com.develop_mouse.gummy_dang.reward.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RewardStatus {
	CURRENT("CURRENT"), PENDING("PENDING"), DONE("DONE");

	private final String rewardStatus;

}
