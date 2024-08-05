package com.develop_mouse.gummy_dang.member.controller;

import com.develop_mouse.gummy_dang.member.domain.request.MemberUpdateRequest;
import com.develop_mouse.gummy_dang.member.domain.response.MemberRetrieveResponse;
import com.develop_mouse.gummy_dang.member.domain.response.MemberUpdateResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.member.domain.request.SignUpRequest;
import com.develop_mouse.gummy_dang.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/sign-up")
	public Response<Void> signUp(@Validated @RequestBody SignUpRequest signUpRequest) {
		return memberService.singUp(signUpRequest);
	}

	//마이페이지 - 회원 정보 조회
	@GetMapping("/member")
	public Response<MemberRetrieveResponse> retrieveMember(){
		return memberService.retrieveMember();
	}

	//마이페이지 - 회원 정보 수정
	@PutMapping("/member")
	public Response<MemberUpdateResponse> updateMember(@RequestBody MemberUpdateRequest request) {
		return memberService.updateMember(request);
	}

	//회원 탈퇴
	@DeleteMapping("/member")
	public Response<Void> deleteMember() {
		return memberService.deleteMember();
	}


}
