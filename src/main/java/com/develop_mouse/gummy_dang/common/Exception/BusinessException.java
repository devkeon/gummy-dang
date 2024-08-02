package com.develop_mouse.gummy_dang.common.Exception;

import com.develop_mouse.gummy_dang.common.domain.ResponseCode;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

	private ResponseCode responseCode;

	public BusinessException(ResponseCode responseCode) {
		super(responseCode.getMessage());
		this.responseCode = responseCode;
	}
}
