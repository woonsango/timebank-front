package com.capstone.timepay.firebase.dto;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FCMDto {
    private String title;
    private String body;
    private String imageUrl;
    private String token;
}
