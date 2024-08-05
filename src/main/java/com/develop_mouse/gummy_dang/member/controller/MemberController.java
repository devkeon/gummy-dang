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

	//마이페이지 - 회원 정보 조회
	@GetMapping("/{id}")
	public ResponseEntity<MemberDTO> retrieveMember(@PathVariable("id") Long id){
		MemberDTO retrieveMember = memberService.retrieveMember(id);
		if (retrieveMember == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(retrieveMember);
	}

	//마이페이지 - 회원 정보 수정
	@PutMapping("/{id}")
	public ResponseEntity<MemberDTO> updateMember(@PathVariable("id") Long id, @RequestBody MemberDTO memberDTO) {
		MemberDTO updatedMember = memberService.updateMember(id, memberDTO);
		if (updatedMember == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(updatedMember);
	}

	//회원 탈퇴
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id) {
		boolean isDeleted = memberService.deleteMember(id);
		if (!isDeleted) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}


}
