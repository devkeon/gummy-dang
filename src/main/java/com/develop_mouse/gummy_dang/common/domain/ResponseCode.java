package com.develop_mouse.gummy_dang.common.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
	OK("COM-000"), MEMBER_NOT_FOUND("MEM-001"), MEMBER_USERNAME_DUPLICATION("MEM-000");

	private final String responseCode;

}
