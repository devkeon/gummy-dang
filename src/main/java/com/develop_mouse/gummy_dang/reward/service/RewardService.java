package com.develop_mouse.gummy_dang.reward.service;

import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.reward.domain.response.RewardInfoResponse;

public interface RewardService {

	void createReward(Double distance);

	Response<RewardInfoResponse> getCurrentRewardInfo();

	Response<Void> rewardPendingToDone();

}
