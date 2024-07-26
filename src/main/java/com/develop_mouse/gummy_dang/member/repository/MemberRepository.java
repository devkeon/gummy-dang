package com.develop_mouse.gummy_dang.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.develop_mouse.gummy_dang.member.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findMemberByUserName(String userName);
}
