package com.develop_mouse.gummy_dang.authentication.handler.oauth;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.develop_mouse.gummy_dang.common.domain.ResponseCode;
import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthLoginFailureHandler implements AuthenticationFailureHandler {
	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {

		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		try {
			String responseBody = objectMapper.writeValueAsString(Response.fail(ResponseCode.MEMBER_NOT_FOUND));
			response.getWriter().write(responseBody);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		log.info("소셜 로그인에 실패했습니다. 에러 메시지 : {}", exception.getMessage());

	}
}
