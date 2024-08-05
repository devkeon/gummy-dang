package com.develop_mouse.gummy_dang.member.service;

import java.util.UUID;

import com.develop_mouse.gummy_dang.authentication.util.SecurityContextUtil;
import com.develop_mouse.gummy_dang.common.Exception.BusinessException;
import com.develop_mouse.gummy_dang.member.domain.request.MemberUpdateRequest;
import com.develop_mouse.gummy_dang.member.domain.response.MemberRetrieveResponse;
import com.develop_mouse.gummy_dang.member.domain.response.MemberUpdateResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.develop_mouse.gummy_dang.common.domain.ResponseCode;
import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.member.domain.Role;
import com.develop_mouse.gummy_dang.member.domain.entity.Member;
import com.develop_mouse.gummy_dang.member.domain.request.SignUpRequest;
import com.develop_mouse.gummy_dang.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final SecurityContextUtil securityContextUtil;

	@Value("${aws.s3.image-url}")
	private String defaultProfileImage;

	@Override
	public Response<Void> singUp(SignUpRequest signUpRequest) {

		Member signUpMember = Member.builder()
			.userName(signUpRequest.getUserName())
			.password(passwordEncoder.encode(signUpRequest.getPassword()))
			.nickname(signUpRequest.getNickname() == null ?
				"임시" + UUID.randomUUID().toString().substring(1, 9)
				: signUpRequest.getNickname())
			.profileImageUrl(defaultProfileImage + "profile/default.jpeg")
			.role(Role.MEMBER)
			.build();

		if (memberRepository.findMemberByUserName(signUpMember.getUserName()).isPresent()) {
			return Response.fail(ResponseCode.MEMBER_USERNAME_DUPLICATION);
		}
		memberRepository.save(signUpMember);

		return Response.ok();
	}

	//조회 메서드
	@Override
	public Response<MemberRetrieveResponse> retrieveMember() {

		Member contextMember = getContextMember();

		MemberRetrieveResponse response = MemberRetrieveResponse.builder()
			.nickname(contextMember.getNickname())
			.profileImageUrl(contextMember.getProfileImageUrl())
			.address(contextMember.getAddress())
			.phoneNumber(contextMember.getPhoneNumber())
			.build();

		return Response.ok(response);

	}

	//수정 메서드
	@Override
	public Response<MemberUpdateResponse> updateMember(MemberUpdateRequest memberUpdateRequest) {

		Member contextMember = getContextMember();

		contextMember.updateNickname(memberUpdateRequest.getNickname());
		contextMember.updatePhoneNumber(memberUpdateRequest.getPhoneNumber());
		contextMember.updateAddress(memberUpdateRequest.getAddress());

		MemberUpdateResponse response = MemberUpdateResponse.builder()
			.nickname(memberUpdateRequest.getNickname())
			.address(memberUpdateRequest.getAddress())
			.phoneNumber(memberUpdateRequest.getPhoneNumber())
			.build();

		return Response.ok(response);
	}

	@Override
	public Response<Void> deleteMember() {

		Member contextMember = getContextMember();

		memberRepository.delete(contextMember);

		return Response.ok();
	}

	private Member getContextMember() {
		Long contextMemberId = securityContextUtil.getContextMemberInfo().getMemberId();

		return memberRepository.findById(contextMemberId).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));
	}

}


