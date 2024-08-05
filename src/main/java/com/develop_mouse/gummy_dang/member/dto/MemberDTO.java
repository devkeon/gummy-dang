package com.develop_mouse.gummy_dang.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private String nickname;
    private String phoneNumber;
    private String address;
    private String profileImageUrl;
}
