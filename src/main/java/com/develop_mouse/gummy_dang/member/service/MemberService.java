package com.develop_mouse.gummy_dang.member.service;

import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.member.domain.request.SignUpRequest;

public interface MemberService {

	Response<Void> singUp(SignUpRequest signUpRequest);

}
