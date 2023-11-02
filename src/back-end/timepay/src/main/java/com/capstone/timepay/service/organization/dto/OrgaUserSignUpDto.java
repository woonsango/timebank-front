package com.capstone.timepay.service.organization.dto;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgaUserSignUpDto {

    private String organizationName;
    private String managerName;
    private String managerPhone;
    private String businessCode;
    private int employeeNum;
    private int timepay;
    private String deviceToken;
    private String id;
    private String pw;

    public static OrgaUserSignUpDto of(String organizationName, String managerName, String managerPhone, String businessCode, int employeeNum, int timepay, String deviceToken, String id, String pw) {
        return new OrgaUserSignUpDto(organizationName,managerName,managerPhone,businessCode,employeeNum,timepay,deviceToken,id,pw);
    }
}
