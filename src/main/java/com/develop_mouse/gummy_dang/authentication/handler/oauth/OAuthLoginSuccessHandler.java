package com.develop_mouse.gummy_dang.authentication.handler.oauth;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.develop_mouse.gummy_dang.authentication.domain.oauth2.CustomOAuth2User;
import com.develop_mouse.gummy_dang.authentication.util.JwtTokenUtil;
import com.develop_mouse.gummy_dang.common.domain.ResponseCode;
import com.develop_mouse.gummy_dang.member.domain.entity.Member;
import com.develop_mouse.gummy_dang.member.repository.MemberRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtTokenUtil jwtTokenUtil;
	private final MemberRepository memberRepository;

	@Value("${jwt.cookie.expire}")
	private Integer COOKIE_EXPIRATION;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		try{
			CustomOAuth2User oAuthUser = (CustomOAuth2User) authentication.getPrincipal();

			Member member = memberRepository.findById(oAuthUser.getMember().getId()).stream()
				.findAny()
				.orElseThrow(() -> new RuntimeException(ResponseCode.MEMBER_NOT_FOUND.getResponseCode()));

			Authentication authentication1 = jwtTokenUtil.createAuthentication(member);

			String accessToken = jwtTokenUtil.generateAccessToken(authentication1);

			String refreshToken = jwtTokenUtil.generateRefreshToken();

			member.updateRefreshToken(refreshToken);

			ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
				.path("/")
				.httpOnly(true)
				.sameSite("None")
				.maxAge(COOKIE_EXPIRATION)
				.secure(true)
				.build();

			response.setHeader("Set-Cookie", cookie.toString());

			String redirectUrl = "https://gummydang-1012152884843.asia-northeast3.run.app/oauth/callback?token=" +
				URLEncoder.encode(accessToken, StandardCharsets.UTF_8) +
				"&nickname=" + URLEncoder.encode(member.getNickname(), StandardCharsets.UTF_8);

			response.sendRedirect(redirectUrl);

		} catch (Exception e){
			throw new RuntimeException("error occur");
		}
	}
}
