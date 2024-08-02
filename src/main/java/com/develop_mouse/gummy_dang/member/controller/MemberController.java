package com.develop_mouse.gummy_dang.member.controller;

import ch.qos.logback.core.model.Model;
import com.develop_mouse.gummy_dang.member.domain.entity.Member;
import com.develop_mouse.gummy_dang.member.dto.MemberDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
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



	//마이페이지 - 수정하기 버튼 눌렀을 때
	@PutMapping("/{id}")
	public ResponseEntity<MemberDTO> updateMember(@PathVariable Long id, @RequestBody MemberDTO memberDTO) {
		MemberDTO updatedMember = memberService.updateMember(id, memberDTO);
		if (updatedMember == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(updatedMember);
	}

	//회원 탈퇴
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
		boolean isDeleted = memberService.deleteMember(id);
		if (!isDeleted) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}


}
