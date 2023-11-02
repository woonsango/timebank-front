package com.capstone.timepay.controller.admin.response.organization;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationMain {
    private Long userId;
    private String id;
    private String organizationName;
    private String imageUrl;
    private String businessNumber;
    private String managerName;
    private String managerPhone;
    private String certificationUrl;
    private String authority;
    private int employeeNum;
    private int timepay;
    private boolean blackList;
}
