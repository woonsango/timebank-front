package com.capstone.timepay.controller.organization.response;

import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.domain.organization.Organization;
import lombok.Getter;

@Getter
public class AuthenticationResponse {
    private final String jwt;
    private final Organization organization;

    public AuthenticationResponse(String jwt, Organization organization) {
        this.jwt = jwt;
        this.organization = organization;
    }
}
