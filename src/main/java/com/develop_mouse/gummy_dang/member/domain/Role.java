package com.develop_mouse.gummy_dang.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	MEMBER("MEMBER"), SOCIAL("SOCIAL");

	private final String role;

}
