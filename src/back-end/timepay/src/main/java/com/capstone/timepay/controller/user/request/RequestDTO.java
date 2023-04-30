package com.capstone.timepay.controller.user.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RequestDTO {
    private String id; // 고유식별번호
    private String imageUrl; // 프로필 이미지
    private String name; // 실명
    private String nickName; // 닉네임
    private String location; // 지역
    private String phone; // 전화번호
    private String introduction; // 소개글
    private String birthday; // 생일, 데이터 타입 변경 가능성 있음

    private String deviceToken; // firebase device token
}

