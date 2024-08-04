package com.develop_mouse.gummy_dang.reward.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.develop_mouse.gummy_dang.authentication.util.SecurityContextUtil;
import com.develop_mouse.gummy_dang.common.Exception.BusinessException;
import com.develop_mouse.gummy_dang.common.domain.ResponseCode;
import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.member.domain.entity.Member;
import com.develop_mouse.gummy_dang.member.repository.MemberRepository;
import com.develop_mouse.gummy_dang.reward.domain.RewardStatus;
import com.develop_mouse.gummy_dang.reward.domain.entity.Reward;
import com.develop_mouse.gummy_dang.reward.domain.response.RewardInfoResponse;
import com.develop_mouse.gummy_dang.reward.repository.RewardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

	private static final Double REWARD_ACHIEVEMENT = 1000D;
	private final RewardRepository rewardRepository;
	private final MemberRepository memberRepository;
	private final SecurityContextUtil securityContextUtil;

	@Override
	public void createReward(Double distance) {

		Long contextMemberId = securityContextUtil.getContextMemberInfo().getMemberId();

		Reward reward = getCurrentReward(contextMemberId);
		Double totalDistance = reward.getPercentage() * REWARD_ACHIEVEMENT;
		Double percentage = Math.min((totalDistance + distance) / REWARD_ACHIEVEMENT, 100D);
		reward.updatePercentage(percentage);

		if (percentage >= 100D) {
			reward.updateRewardStatus(RewardStatus.PENDING);

			Member member = memberRepository.findById(contextMemberId).stream()
				.findAny()
				.orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

			Reward createdReward = Reward.builder()
				.member(member)
				.percentage(0D)
				.rewardStatus(RewardStatus.CURRENT)
				.build();

			rewardRepository.save(createdReward);
		}
	}

	@Override
	public Response<RewardInfoResponse> getCurrentRewardInfo() {

		Long contextMemberId = securityContextUtil.getContextMemberInfo().getMemberId();

		List<Reward> rewardList = rewardRepository.findByMemberIdCurrentAndPending(contextMemberId);

		Optional<Reward> pendingReward = rewardList.stream()
			.filter(reward -> reward.getRewardStatus() == RewardStatus.PENDING)
			.findAny();

		if (pendingReward.isPresent()) {
			return Response.ok(new RewardInfoResponse(100D, RewardStatus.PENDING));
		}

		Reward currentReward = rewardList.stream()
			.filter(reward -> reward.getRewardStatus() == RewardStatus.CURRENT)
			.findAny()
			.orElseGet(
				() -> {
					Member member = memberRepository.findById(contextMemberId).stream()
						.findAny()
						.orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));
					Reward newReward = Reward.builder()
						.rewardStatus(RewardStatus.CURRENT)
						.percentage(0D)
						.member(member)
						.build();

					return rewardRepository.save(newReward);
				}
			);

		return Response.ok(new RewardInfoResponse(currentReward.getPercentage(), currentReward.getRewardStatus()));
	}

	@Override
	public Response<Void> rewardPendingToDone() {

		Long contextMemberId = securityContextUtil.getContextMemberInfo().getMemberId();

		rewardRepository.findByMemberIdCurrentAndPending(contextMemberId).stream()
			.filter(reward -> reward.getRewardStatus() == RewardStatus.PENDING)
			.forEach(reward -> reward.updateRewardStatus(RewardStatus.DONE));

		return Response.ok();
	}

	private Reward getCurrentReward(Long contextMemberId) {

		return rewardRepository.findByMemberIdOnlyCurrent(contextMemberId, RewardStatus.CURRENT)
			.orElseGet(() -> {

				Member member = memberRepository.findById(contextMemberId).stream()
					.findAny()
					.orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

				Reward newReward = Reward.builder()
					.percentage(0D)
					.rewardStatus(RewardStatus.CURRENT)
					.member(member)
					.build();
				return rewardRepository.save(newReward);
			});
	}
}
