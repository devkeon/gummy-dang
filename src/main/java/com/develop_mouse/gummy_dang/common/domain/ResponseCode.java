package com.develop_mouse.gummy_dang.common.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
	OK("COM-000", "Ok."),
	MEMBER_NOT_FOUND("MEM-001", "No such member exist."),
	MEMBER_USERNAME_DUPLICATION("MEM-000", "Username already exists."),
	POST_NOT_FOUND("POST-000","No such post exist."),
	POST_AUTHOR_DIFFERENCE("POST-001","User and author are different");

	private final String responseCode;
	private final String message;

}
