package com.capstone.timepay.service.organization.dto;


import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgUserInfoDTO {
    private String organizationName;
    private String managerName;
    private String managerPhone;
    private String businessCode;
    private int employeeNum;
    private int timepay;
    private String id;
    private String imageUrl;
    private String certificationUrl;

}
