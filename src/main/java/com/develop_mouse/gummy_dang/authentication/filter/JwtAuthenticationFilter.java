package com.develop_mouse.gummy_dang.authentication.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.develop_mouse.gummy_dang.authentication.util.JwtTokenUtil;
import com.develop_mouse.gummy_dang.common.Exception.BusinessException;
import com.develop_mouse.gummy_dang.common.domain.ResponseCode;
import com.develop_mouse.gummy_dang.member.domain.entity.Member;
import com.develop_mouse.gummy_dang.member.repository.MemberRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenUtil jwtTokenUtil;
	private final MemberRepository memberRepository;

	@Value("${jwt.cookie.expire}")
	private Integer COOKIE_EXPIRATION;

	private static String GRANT_TYPE = "Bearer ";

	protected List<String> filterPassList = List.of("/api", "/api/login", "/probe", "/oauth2/authorization/kakao",
		"/login/oauth2/code/kakao", "/favicon.ico", "/api/sign-up");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		if (filterPassList.contains(request.getRequestURI())){
			filterChain.doFilter(request, response);
			return;
		}

		String accessToken = jwtTokenUtil.extractAccessToken(request).stream()
			.findAny()
			.orElseThrow(() -> new BusinessException(ResponseCode.ACCESS_TOKEN_NOT_FOUND));

		Authentication authentication;

		// 정상 흐름
		try{
			authentication = jwtTokenUtil.getAuthentication(accessToken);

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String refreshToken = String.valueOf(jwtTokenUtil.extractRefreshToken(request));

			if (refreshToken == null){
				throw new BusinessException(ResponseCode.REFRESH_TOKEN_NOT_FOUND);
			}

			response.setHeader("Authorization", GRANT_TYPE + accessToken);
			response.setHeader("Set-Cookie", refreshToken);

			// access token 만료 흐름
		} catch (ExpiredJwtException e){

			log.info("access token expired = {}", accessToken);

			Claims claims = e.getClaims();

			String refreshToken = jwtTokenUtil.extractRefreshToken(request).stream()
				.findAny()
				.orElseThrow(() -> new BusinessException(ResponseCode.REFRESH_TOKEN_NOT_FOUND));

			if (!jwtTokenUtil.validate(refreshToken)){
				throw new BusinessException(ResponseCode.MEMBER_LOGIN_SESSION_EXPIRED);
			}

			Member currentMember = memberRepository.findById(Long.parseLong(claims.get("id").toString())).stream()
				.findAny()
				.orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

			if (!currentMember.getRefreshToken().equals(refreshToken)){
				throw new BusinessException(ResponseCode.MEMBER_LOGIN_SESSION_EXPIRED);
			}

			String generateRefreshToken = jwtTokenUtil.generateRefreshToken();

			currentMember.updateRefreshToken(generateRefreshToken);
			memberRepository.save(currentMember);

			Authentication createdAuthentication = jwtTokenUtil.createAuthentication(currentMember);

			String generatedAccessToken = jwtTokenUtil.generateAccessToken(createdAuthentication);

			response.setHeader("Authorization", generatedAccessToken);

			ResponseCookie cookie = ResponseCookie.from("refreshToken", generateRefreshToken)
				.path("/")
				.httpOnly(true)
				.maxAge(COOKIE_EXPIRATION)
				.sameSite("Lax")
				.secure(false)
				.build();

			response.setHeader("Set-Cookie", String.valueOf(cookie));

			SecurityContextHolder.getContext().setAuthentication(createdAuthentication);

		} catch (Exception e){
			throw new RuntimeException(e);
		}

		filterChain.doFilter(request, response);

	}
}
