package com.capstone.timepay.controller.admin.request.userManage;

import com.capstone.timepay.service.admin.dto.UserUpdateDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birth;
    private String region;


    public UserUpdateDto toServiceDto() {
        return UserUpdateDto.of(userId, nickname, name, birth, region);
    }
}
