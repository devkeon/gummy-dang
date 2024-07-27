package com.develop_mouse.gummy_dang.common.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
	OK("COM-000", "Ok."),
	MEMBER_NOT_FOUND("MEM-001", "No such member exist."),
	MEMBER_USERNAME_DUPLICATION("MEM-000", "Username already exists.");

	private final String responseCode;
	private final String message;

}
