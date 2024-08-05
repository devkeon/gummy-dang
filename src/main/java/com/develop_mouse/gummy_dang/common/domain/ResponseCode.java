package com.develop_mouse.gummy_dang.common.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
	OK("COM-000", "Ok."),
	MEMBER_LOGIN_SESSION_EXPIRED("MEM-111", "Login session expired.(refresh cookie expired)"),
	MEMBER_NOT_FOUND("MEM-001", "No such member exist."),
	MEMBER_USERNAME_DUPLICATION("MEM-000", "Username already exists."),
	MEMBER_UNAUTHORIZED("MEM-002", "Member not matched."),
	WALK_RECORD_NOT_FOUND("WRC-001", "No such walk record exists."),
	GUMMY_IMG_NOT_FOUND("GUM-001", "No such gummy exists."),
	SERVER_ERROR("SEV-001", "Server went wrong."),
	ACCESS_TOKEN_NOT_FOUND("SEV-002", "There's no access token."),
	REFRESH_TOKEN_NOT_FOUND("SEV-003", "There's no refresh token."),
	REWARD_NOT_FOUND("RWD-001", "There's no reward. Check out your Database."),
	POST_NOT_FOUND("PST-000","No such post exist."),
	POST_WRITER_DIFFERENCE("PST-001","User and author are different"),
	OVER_NUM_COORDINATES("PST-002", "A post can have at most 6 coordinates."),
	COORDINATE_NOT_FOUND("PST-003", "No such coordinate exist."),
	IMAGE_NOT_FOUND("IMG-001", "There's no image onb request.")
	;

	private final String responseCode;
	private final String message;

}
