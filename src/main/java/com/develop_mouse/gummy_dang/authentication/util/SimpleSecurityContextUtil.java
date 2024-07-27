package com.develop_mouse.gummy_dang.authentication.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.develop_mouse.gummy_dang.authentication.domain.JwtMemberDetail;

import lombok.Getter;

@Getter
@Component
public class SimpleSecurityContextUtil implements SecurityContextUtil {

	@Override
	public JwtMemberDetail getContextMemberInfo() {
		return (JwtMemberDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
