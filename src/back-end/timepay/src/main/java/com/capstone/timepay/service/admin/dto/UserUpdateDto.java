package com.capstone.timepay.service.admin.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    private Long userId;

    private String nickname;

    private String name;

    private LocalDateTime birth;

    private String region;

    public static UserUpdateDto of(Long userId, String nickname, String name, LocalDateTime birth, String region){
        return new UserUpdateDto(userId, nickname, name, birth, region);
    }

}
