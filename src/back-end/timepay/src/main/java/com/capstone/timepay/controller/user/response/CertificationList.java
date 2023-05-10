package com.capstone.timepay.controller.user.response;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationList {
    private Long boardId;
    private String title;
    private String organizationName;
    private String managerName;
    private String managerPhone;
    private int volunteerTime;
    private boolean state;
    private String certificationUrl;
}
