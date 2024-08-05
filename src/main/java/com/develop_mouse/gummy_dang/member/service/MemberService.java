package com.develop_mouse.gummy_dang.member.service;

import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.member.domain.request.SignUpRequest;
import com.develop_mouse.gummy_dang.member.domain.request.MemberUpdateRequest;
import com.develop_mouse.gummy_dang.member.domain.response.MemberRetrieveResponse;
import com.develop_mouse.gummy_dang.member.domain.response.MemberUpdateResponse;

public interface MemberService {

	Response<Void> singUp(SignUpRequest signUpRequest);
	//회원 정보 조회
	Response<MemberRetrieveResponse> retrieveMember();
	//회원 정보 수정
	Response<MemberUpdateResponse> updateMember(MemberUpdateRequest memberDTO);
	//회원 탈퇴
	Response<Void> deleteMember();

}
