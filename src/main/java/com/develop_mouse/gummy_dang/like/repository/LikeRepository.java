package com.develop_mouse.gummy_dang.like.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.develop_mouse.gummy_dang.like.domain.entity.Like;
import com.develop_mouse.gummy_dang.member.domain.entity.Member;
import com.develop_mouse.gummy_dang.post.domain.entity.Post;

public interface LikeRepository extends JpaRepository<Like, Long> {

	Optional<Like> findByMemberAndPost(Member member, Post post);
}
