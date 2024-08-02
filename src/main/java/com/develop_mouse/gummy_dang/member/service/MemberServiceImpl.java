package com.develop_mouse.gummy_dang.member.service;

import java.util.Optional;
import java.util.UUID;

import com.develop_mouse.gummy_dang.common.domain.ActiveStatus;
import com.develop_mouse.gummy_dang.member.dto.MemberDTO;
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

	//수정 메서드
	@Override
	public MemberDTO updateMember(Long id, MemberDTO memberDTO) {
		Optional<Member> optionalMember = memberRepository.findById(id);
		if (optionalMember.isEmpty()) {
			return null;
		}

		Member member = optionalMember.get();
		member.updateNickname(memberDTO.getNickname());
		member.updatePhoneNumber(memberDTO.getPhoneNumber());
		member.updateAddress(memberDTO.getAddress());

		Member updatedMember = memberRepository.save(member);

		return new MemberDTO(updatedMember.getNickname(), updatedMember.getAddress(), updatedMember.getPhoneNumber());
	}

	//탈퇴 메서드
	@Override
	public boolean deleteMember(Long id) {
		Optional<Member> optionalMember = memberRepository.findById(id);
		if (optionalMember.isEmpty()) {
			return false;
		}

		Member member = optionalMember.get();
		member.updateActiveStatus(ActiveStatus.DELETED);
		memberRepository.save(member);
		return true;
	}



	
}


