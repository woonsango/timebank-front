package com.capstone.timepay.controller.organization.response;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipateUsers {
    private Long userId;
    private String userName;
    private String userNickname;
    private String email;
    private String phone;
    private String certificationUrl;
    private boolean isPublished;
}
