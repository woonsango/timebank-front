package com.capstone.timepay.controller.user.response;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;


@Data
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ResponseDTO {
    private Long uid; // 고유식별번호
    private String imageUrl; // 프로필 이미지

    private String nickName; // 닉네임

    private int timePay; // 타임페이, userProfile에서 가져옴

    public ResponseDTO(Long uid, String imageUrl, String nickName, int timePay){
        this.uid = uid;
        this.imageUrl = imageUrl;
        this.nickName = nickName;
        this.timePay = timePay;
    }
}