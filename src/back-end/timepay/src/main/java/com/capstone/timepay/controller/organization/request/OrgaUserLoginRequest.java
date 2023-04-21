package com.capstone.timepay.controller.organization.request;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgaUserLoginRequest {
    private String id;
    private String pw;
}
