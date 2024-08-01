package com.develop_mouse.gummy_dang.reward.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.develop_mouse.gummy_dang.reward.domain.entity.Reward;

public interface RewardRepository extends JpaRepository<Reward, Long> {
}
