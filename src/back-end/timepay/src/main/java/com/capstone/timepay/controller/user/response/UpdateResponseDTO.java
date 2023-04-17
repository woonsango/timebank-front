package com.capstone.timepay.controller.user.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UpdateResponseDTO {
    private Long id;
    private String imageUrl;
    private String name;
    private String nickName;
    private String sex;
    // private String age;
    private String location;
    private String introduction;

    public UpdateResponseDTO(Long id, String imageUrl, String name, String nickName, String sex, String location, String introduction){
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.nickName = nickName;
        this.sex = sex;
        this.location = location;
        this.introduction = introduction;
    }

}
