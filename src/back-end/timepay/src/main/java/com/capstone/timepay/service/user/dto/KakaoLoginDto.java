package com.capstone.timepay.service.user.dto;

import com.capstone.timepay.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoLoginDto {
    private Long id;
    private String email; // 카카오 이메일
    private String sex; // 성별
    //private String birthday; // 생일


    public static KakaoLoginDto toKaKaoLoginDto(User user){
        return new KakaoLoginDto(
                user.getUid(),
                user.getEmail(),
                user.getSex()
                //user.getBirthday()
        );
    }
}
