package com.develop_mouse.gummy_dang.common.filter;

import java.io.IOException;
import java.rmi.ServerException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.develop_mouse.gummy_dang.common.Exception.BusinessException;
import com.develop_mouse.gummy_dang.common.domain.ResponseCode;
import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

	public final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			filterChain.doFilter(request, response);
		} catch (BusinessException exception) {

			log.error("BusinessException = {}", exception.getMessage());
			log.error("stack trace = {}", exception.getStackTrace());

			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding("UTF-8");

			setBusinessExceptionResponse(response, exception.getResponseCode());

		} catch (Exception exception) {

			log.error("Exception = {}", exception.getMessage());
			log.error("stack trace = {}", exception.getStackTrace());

			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding("UTF-8");

			setExceptionResponse(response, exception);

		}

	}

	private void setBusinessExceptionResponse(HttpServletResponse response, ResponseCode responseCode) {
		try{
			response.getWriter().write(objectMapper.writeValueAsString(Response.fail(responseCode)));
		} catch (IOException e){
			log.error("check exception filter = {}", responseCode.getMessage());
			log.error(e.getMessage());
		}

	}

	private void setExceptionResponse(HttpServletResponse response, Exception exception) {
		try{
			response.getWriter().write(objectMapper.writeValueAsString(Response.fail(ResponseCode.SERVER_ERROR)));
		} catch (IOException e){
			log.error("check exception filter");
			log.error(e.getMessage());
		}

	}
}
