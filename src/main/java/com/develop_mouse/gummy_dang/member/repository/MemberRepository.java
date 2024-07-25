package com.develop_mouse.gummy_dang.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.develop_mouse.gummy_dang.member.domain.entity.Member;

public abstract class MemberRepository implements JpaRepository<Member, Long> {
}
