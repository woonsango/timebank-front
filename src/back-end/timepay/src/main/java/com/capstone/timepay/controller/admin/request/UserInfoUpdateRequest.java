package com.capstone.timepay.controller.admin.request;

import com.capstone.timepay.service.admin.dto.UserUpdateDto;
import com.sun.istack.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoUpdateRequest {

    @NotNull
    private Long userId;
    private String nickname;
    private String name;
    private LocalDateTime birth;
    private String region;


    public UserUpdateDto toServiceDto() {
        return UserUpdateDto.of(userId, nickname, name, birth, region);
    }
}
