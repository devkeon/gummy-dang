package com.develop_mouse.gummy_dang.member.service;

import java.util.UUID;

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

	@Override
	public Response<Void> singUp(SignUpRequest signUpRequest) {

		Member signUpMember = Member.builder()
			.userName(signUpRequest.getUserName())
			.password(passwordEncoder.encode(signUpRequest.getPassword()))
			.nickname(signUpRequest.getNickname() == null ?
				"임시" + UUID.randomUUID().toString().substring(1, 9)
				: signUpRequest.getNickname())
			.role(Role.MEMBER)
			.build();

		if (memberRepository.findMemberByUserName(signUpMember.getUserName()).isPresent()) {
			return Response.fail(ResponseCode.MEMBER_USERNAME_DUPLICATION);
		}
		memberRepository.save(signUpMember);

		return Response.ok();
	}

}
