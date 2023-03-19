package com.capstone.timepay.controller.admin.response;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainResponse {

    private Long userId;
    private String userName;
    private String nickName;
    private String sex;
    private LocalDateTime birth;
    private String region;
    private int timepay;

}
