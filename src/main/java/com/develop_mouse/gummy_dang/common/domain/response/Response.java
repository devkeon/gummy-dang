package com.develop_mouse.gummy_dang.common.domain.response;

import com.develop_mouse.gummy_dang.common.domain.ResponseCode;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

	private String code;
	private T data;

	public Response(String code) {
		this.code = code;
	}

	public static <T> Response<T> ok(T data){
		return new Response<>(ResponseCode.OK.getResponseCode(), data);
	}

	public static <T> Response<T> ok() {
		return new Response<>(ResponseCode.OK.getResponseCode());
	}

	public static <T> Response<T> fail(ResponseCode responseCode) {
		return new Response<>(responseCode.getResponseCode());
	}

}
