package com.capstone.timepay.controller.organization.request;

import com.capstone.timepay.service.organization.dto.OrgaUserSignUpDto;
import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgaUserSignUpRequest {
    @NotNull
    private String organizationName;
    @NotNull
    private String managerName;
    @NotNull
    private String managerPhone;
    private String businessCode;
    private int employeeNum;
    private int timepay;
    @NotNull
    private String id;
    @NotNull
    private String pw;

    public OrgaUserSignUpDto toServiceDto(){
        return OrgaUserSignUpDto.of(organizationName,managerName,managerPhone,businessCode,employeeNum,timepay,id,pw);
    }

}
