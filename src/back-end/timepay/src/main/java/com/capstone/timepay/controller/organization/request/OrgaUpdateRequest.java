package com.capstone.timepay.controller.organization.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgaUpdateRequest {
    private String managerName;
    private String managerPhone;
}
