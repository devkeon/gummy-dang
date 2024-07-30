package com.develop_mouse.gummy_dang.common.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
	OK("COM-000", "Ok."),
	MEMBER_NOT_FOUND("MEM-001", "No such member exist."),
	MEMBER_USERNAME_DUPLICATION("MEM-000", "Username already exists."),
	MEMBER_UNAUTHORIZED("MEM-002", "Member not matched."),
	WALK_RECORD_NOT_FOUND("WRC-001", "No such walk record exists."),
	GUMMY_IMG_NOT_FOUND("GUM-001", "No such gummy exists.")
	;

	private final String responseCode;
	private final String message;

}
