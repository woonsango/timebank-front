package com.capstone.timepay.service.admin.dto;

import lombok.*;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRegisterDTO {

    private String adminName;   // 로그인 할 때 쓰는 아이디
    private String password;
    private String authority;
    private String name;    // 관리자 이름
    private String email;
    private String phone;

}
