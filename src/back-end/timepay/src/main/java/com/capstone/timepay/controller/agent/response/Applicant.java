package com.capstone.timepay.controller.agent.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Applicant {
    private Long appliUid;
    private String appliName;

    public void setAppliUid(Long appliUid) { this.appliUid = appliUid; }

    public void setAppliName(String appliName) {
        this.appliName = appliName;
    }
}
