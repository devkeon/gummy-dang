package com.develop_mouse.gummy_dang.authentication.handler.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import com.develop_mouse.gummy_dang.authentication.domain.response.LoginSuccessResponse;
import com.develop_mouse.gummy_dang.authentication.util.JwtTokenUtil;
import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.member.domain.entity.Member;
import com.develop_mouse.gummy_dang.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final MemberRepository memberRepository;
	private final JwtTokenUtil jwtTokenProvider;
	private final ObjectMapper objectMapper;

	@Value("${jwt.cookie.expire}")
	private Integer COOKIE_EXPIRATION;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		UserDetails jwtMemberDetail = (UserDetails) authentication.getPrincipal();

		Member member = memberRepository.findMemberByUserName(jwtMemberDetail.getUsername()).stream()
			.findAny()
			.orElseThrow(() -> new RuntimeException("no such member"));

		Authentication genAuthentication = jwtTokenProvider.createAuthentication(member);

		String accessToken = jwtTokenProvider.generateAccessToken(genAuthentication);
		String refreshToken = jwtTokenProvider.generateRefreshToken();

		member.updateRefreshToken(refreshToken);

		response.setHeader("Authorization", accessToken);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		LoginSuccessResponse loginSuccessResponse = LoginSuccessResponse.of(member.getNickname());

		try {
			String responseBody = objectMapper.writeValueAsString(Response.ok(loginSuccessResponse));
			response.getWriter().write(responseBody);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
			.path("/")
			.httpOnly(true)
			.maxAge(COOKIE_EXPIRATION)
			.sameSite("None")
			.secure(true)
			.build();

		response.setHeader("Set-Cookie", cookie.toString());

		member.updateRefreshToken(refreshToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}


}
