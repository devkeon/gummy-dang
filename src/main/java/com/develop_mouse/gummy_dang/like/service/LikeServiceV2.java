package com.develop_mouse.gummy_dang.like.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.develop_mouse.gummy_dang.authentication.util.SecurityContextUtil;
import com.develop_mouse.gummy_dang.common.Exception.BusinessException;
import com.develop_mouse.gummy_dang.common.domain.ResponseCode;
import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.like.domain.LikeCount;
import com.develop_mouse.gummy_dang.like.domain.LikeResponse;
import com.develop_mouse.gummy_dang.like.domain.entity.Like;
import com.develop_mouse.gummy_dang.like.repository.LikeRepository;
import com.develop_mouse.gummy_dang.member.domain.entity.Member;
import com.develop_mouse.gummy_dang.member.repository.MemberRepository;
import com.develop_mouse.gummy_dang.post.domain.entity.Post;
import com.develop_mouse.gummy_dang.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeServiceV2 {

	private final LikeRepository likeRepository;
	private final PostRepository postRepository;
	private final SecurityContextUtil securityContextUtil;
	private final MemberRepository memberRepository;

	public Response<LikeResponse> createLike(Long postId) {
		// Member 정보 조회
		Member member = memberRepository.findById(securityContextUtil.getContextMemberInfo().getMemberId())
			.stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

		// Post 정보 조회
		Post post = postRepository.findById(postId).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.POST_NOT_FOUND));

		likeRepository.findByMemberAndPost(member, post).ifPresent(like -> {
			throw new BusinessException(ResponseCode.LIKE_DUPLICATE);
		});

		Like like = Like.builder()
			.member(member)
			.post(post)
			.build();

		Like savedLike = likeRepository.save(like);

		return Response.ok(new LikeResponse(savedLike.getId(), post.getId()));
	}

	public Response<Void> deleteLike(Long postId) {
		// Member 정보 조회
		Member member = memberRepository.findById(securityContextUtil.getContextMemberInfo().getMemberId())
			.stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

		// Post 정보 조회
		Post post = postRepository.findById(postId).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.POST_NOT_FOUND));

		Like like = likeRepository.findByMemberAndPost(member, post).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.LIKE_NOT_FOUND));

		if (like.getMember() != member) {
			throw new BusinessException(ResponseCode.MEMBER_UNAUTHORIZED);
		}

		likeRepository.delete(like);

		return Response.ok();
	}

	public Response<LikeCount> getLikeCount(Long postId) {
		Post post = postRepository.findByIdIs(postId).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.POST_NOT_FOUND));

		return Response.ok(new LikeCount(post.getLike().size()));
	}

}
