package com.develop_mouse.gummy_dang.member.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateResponse {

	private String nickname;
	private String phoneNumber;
	private String address;

}
