package com.develop_mouse.gummy_dang.member.service;

import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.member.domain.entity.Member;
import com.develop_mouse.gummy_dang.member.domain.request.SignUpRequest;
import com.develop_mouse.gummy_dang.member.dto.MemberDTO;

public interface MemberService {

	Response<Void> singUp(SignUpRequest signUpRequest);
	//회원 정보 조회
	MemberDTO retrieveMember(Long id);
	//회원 정보 수정
	MemberDTO updateMember(Long id, MemberDTO memberDTO);
	//회원 탈퇴
	boolean deleteMember(Long id);

}
