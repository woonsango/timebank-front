package com.capstone.timepay.service.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KaKaoLoginDto {

    private String email; // 카카오 이메일
    private String sex; // 성별
    private String birthday; // 생일


    public KaKaoLoginDto(String email, String sex, String birthday){
        this.email = email;
        this.sex = sex;
        this.birthday = birthday;
    }
}
