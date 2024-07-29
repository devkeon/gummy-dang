package com.develop_mouse.gummy_dang.walkrecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.develop_mouse.gummy_dang.walkrecord.domain.entity.WalkRecord;

public interface WalkRecordRepository extends JpaRepository<WalkRecord, Long> {
}
