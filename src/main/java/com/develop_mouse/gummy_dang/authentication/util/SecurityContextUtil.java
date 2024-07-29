package com.develop_mouse.gummy_dang.authentication.util;

import com.develop_mouse.gummy_dang.authentication.domain.JwtMemberDetail;

public interface SecurityContextUtil {

	JwtMemberDetail getContextMemberInfo();

}
