package com.develop_mouse.gummy_dang.common.Exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.develop_mouse.gummy_dang.common.domain.response.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public Response<Void> handleBusinessException(BusinessException businessException) {

		log.error(businessException.getMessage());

		return Response.fail(businessException.getResponseCode());
	}

}
