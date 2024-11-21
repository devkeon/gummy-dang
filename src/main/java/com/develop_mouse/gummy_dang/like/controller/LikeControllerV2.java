package com.develop_mouse.gummy_dang.like.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.like.domain.LikeCount;
import com.develop_mouse.gummy_dang.like.domain.LikeResponse;
import com.develop_mouse.gummy_dang.like.service.LikeServiceV2;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeControllerV2 {

	private final LikeServiceV2 likeService;

	/**
	 * 좋아요 누르기
	 * @param postId : 누르고자 하는 게시글 id
	 */
	@PostMapping("/post/{postId}/like")
	public Response<LikeResponse> createLike(@PathVariable Long postId) {
		return likeService.createLike(postId);
	}

	/**
	 * 좋아요 취소
	 * @param postId : 취소하고자 하는 게시글 id
	 */
	@DeleteMapping("/post/{postId}/like")
	public Response<Void> deleteLike(@PathVariable Long postId) {
		return likeService.deleteLike(postId);
	}

	/**
	 * 게시글의 총 좋아요 수 가져오기
	 * @param postId : 조회하고자 하는 게시글 id
	 */
	@GetMapping("/post/{postId}/like-count")
	public Response<LikeCount> getLikeCount(@PathVariable Long postId) {
		return likeService.getLikeCount(postId);
	}

}
