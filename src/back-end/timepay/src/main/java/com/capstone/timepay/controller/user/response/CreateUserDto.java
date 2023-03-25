package com.capstone.timepay.controller.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
    private String imageUrl;
    private String name;
    private String nickname;
    private String location;
    private String phone;
    private String introduction;

}
