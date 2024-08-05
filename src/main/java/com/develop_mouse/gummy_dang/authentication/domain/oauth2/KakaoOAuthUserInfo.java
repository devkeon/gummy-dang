package com.develop_mouse.gummy_dang.authentication.domain.oauth2;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KakaoOAuthUserInfo extends Oauth2UserInfo{

	public KakaoOAuthUserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public Long getId() {
		return (Long) attributes.get("id");
	}

	@Override
	public String getNickname() {
		Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

		if (kakaoAccount == null) return null;

		Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

		if (profile == null) return null;

		return (String) profile.get("nickname");
	}

	@Override
	public String getEmail() {
		Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

		log.info("kakao_acount={}", kakaoAccount);
		if (kakaoAccount == null) return null;

		boolean isEmailVerified = (boolean) kakaoAccount.get("is_email_verified");
		boolean isEmailValid = (boolean) kakaoAccount.get("is_email_valid");

		if (!isEmailValid || !isEmailVerified) return null;

		return (String) kakaoAccount.get("email");
	}

	@Override
	public String getProfileImage() {
		Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

		if (kakaoAccount == null) return null;

		Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

		if (profile == null) return null;

		return (String) profile.get("thumbnail_image_url");
	}
}
