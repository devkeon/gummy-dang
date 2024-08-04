package com.develop_mouse.gummy_dang.reward.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.develop_mouse.gummy_dang.reward.domain.RewardStatus;
import com.develop_mouse.gummy_dang.reward.domain.entity.Reward;

public interface RewardRepository extends JpaRepository<Reward, Long> {

	@Query(value = "select r from Reward r where r.member.id=:memberId and r.rewardStatus = :rewardStatus")
	Optional<Reward> findByMemberIdOnlyCurrent(@Param("memberId") Long memberId, @Param("rewardStatus")RewardStatus rewardStatus);

	@Query(value = "select r from Reward r where r.member.id=:memberId and r.rewardStatus <> 'DONE'")
	List<Reward> findByMemberIdCurrentAndPending(@Param("memberId") Long memberId);

}
