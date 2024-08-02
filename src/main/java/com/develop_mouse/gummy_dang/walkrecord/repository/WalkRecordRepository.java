package com.develop_mouse.gummy_dang.walkrecord.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.develop_mouse.gummy_dang.member.domain.entity.Member;
import com.develop_mouse.gummy_dang.walkrecord.domain.entity.WalkRecord;

public interface WalkRecordRepository extends JpaRepository<WalkRecord, Long> {

	@EntityGraph(attributePaths = {"recordImages", "member"})
	Optional<WalkRecord> findWalkRecordById(Long id);

	@EntityGraph(attributePaths = {"gummy"})
	List<WalkRecord> findWalkRecordsByMember(Member member);

}
