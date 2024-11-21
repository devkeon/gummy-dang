package com.develop_mouse.gummy_dang.post.repository;

import java.util.List;
import java.util.Optional;

import com.develop_mouse.gummy_dang.post.domain.entity.Post;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long>{

	@EntityGraph(attributePaths = {"like"})
	Optional<Post> findByIdIs(Long postId);

	@EntityGraph(attributePaths = {"member"})
	List<Post> findAll();
}

