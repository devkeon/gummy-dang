package com.develop_mouse.gummy_dang.reward.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.reward.domain.response.RewardInfoResponse;
import com.develop_mouse.gummy_dang.reward.service.RewardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RewardController {

	private final RewardService rewardService;

	@GetMapping("/reward")
	Response<RewardInfoResponse> getCurrentRewardInfo() {
		return rewardService.getCurrentRewardInfo();
	}

	@PatchMapping("/reward/done")
	Response<Void> rewardChangeToDone() {
		return rewardService.rewardPendingToDone();
	}

}
