package com.capstone.timepay.service.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostAdminDTO {
    private String adminName;   // 로그인 할 때 쓰는 아이디
    private String password;
    private String authority;
    private String name;    // 관리자 이름
    private String email;
    private String phone;
}
