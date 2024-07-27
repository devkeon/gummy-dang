package com.develop_mouse.gummy_dang.authentication.domain.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginSuccessResponse {

	private String userName;

	public static LoginSuccessResponse of(String userName) {
		return new LoginSuccessResponse(userName);
	}

}
