package com.develop_mouse.gummy_dang.image.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.develop_mouse.gummy_dang.authentication.util.SecurityContextUtil;
import com.develop_mouse.gummy_dang.common.Exception.BusinessException;
import com.develop_mouse.gummy_dang.common.domain.ResponseCode;
import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.image.domain.response.ImageLocation;
import com.develop_mouse.gummy_dang.image.domain.response.ImageResponse;
import com.develop_mouse.gummy_dang.image.util.AwsS3ImageUtil;
import com.develop_mouse.gummy_dang.member.domain.entity.Member;
import com.develop_mouse.gummy_dang.member.repository.MemberRepository;
import com.develop_mouse.gummy_dang.post.domain.entity.Post;
import com.develop_mouse.gummy_dang.post.repository.PostRepository;
import com.develop_mouse.gummy_dang.walkrecord.domain.entity.WalkRecord;
import com.develop_mouse.gummy_dang.walkrecord.repository.WalkRecordRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AwsS3ImageUploadService implements ImageUploadService{

	private final AwsS3ImageUtil awsS3ImageUtil;
	private final SecurityContextUtil securityContextUtil;
	private final MemberRepository memberRepository;
	private final WalkRecordRepository walkRecordRepository;
	private final PostRepository postRepository;

	private final static String PROFILE_PREFIX = "profile/";
	private final static String POST_PREFIX = "post/";
	private final static String RECORD_PREFIX = "record/";

	@Override
	public Response<ImageResponse<String>> uploadProfileImage(MultipartFile multipartFile) throws IOException {

		Long memberId = securityContextUtil.getContextMemberInfo().getMemberId();

		Member contextMember = memberRepository.findById(memberId).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));;

		String filePath = PROFILE_PREFIX + contextMember.getId();
		String imagerUrl = awsS3ImageUtil.uploadProfileImage(multipartFile, filePath);

		contextMember.updateProfileImageUrl(imagerUrl);

		return Response.ok(ImageResponse.of(imagerUrl));
	}


	@Override
	public Response<ImageResponse<String>> uploadPostImage(MultipartFile multipartFile, Long postId) throws
		IOException {

		Post post = postRepository.findById(postId).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.POST_NOT_FOUND));

		String filePath = POST_PREFIX + postId;
		String imageUrl = awsS3ImageUtil.uploadProfileImage(multipartFile, filePath);

		post.updateImageUrl(imageUrl);

		return Response.ok(ImageResponse.of(imageUrl));
	}

	@Override
	public Response<ImageResponse<String>> uploadWalkRecordImage(MultipartFile multipartFile, Long walkRecordId) throws
		IOException {

		WalkRecord walkRecord = walkRecordRepository.findById(walkRecordId).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.WALK_RECORD_NOT_FOUND));

		String filePath = RECORD_PREFIX + walkRecordId;
		String imagerUrl = awsS3ImageUtil.uploadProfileImage(multipartFile, filePath);

		walkRecord.updateRecordImage(imagerUrl);

		return Response.ok(ImageResponse.of(imagerUrl));
	}
}
