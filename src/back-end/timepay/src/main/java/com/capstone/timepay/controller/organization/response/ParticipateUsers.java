package com.capstone.timepay.controller.organization.response;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipateUsers {
    private String imageUrl;
    private String userNickName;
    private boolean isPublished;
}
