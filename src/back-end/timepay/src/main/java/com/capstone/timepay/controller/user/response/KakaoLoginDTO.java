package com.capstone.timepay.controller.user.response;


import lombok.Getter;

@Getter
public class KakaoLoginDTO {

    private final Boolean signUp;
    private final Long uid;

    public KakaoLoginDTO(Boolean signUp, Long uid){
        this.signUp = signUp;
        this.uid = uid;
    }
}
