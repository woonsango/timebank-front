package com.capstone.timepay.model;

import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.domain.user.User;
import lombok.Getter;

@Getter
public class AuthenticationResponse {
    private final String jwt;
    private final Admin admin;

    public AuthenticationResponse(String jwt, Admin admin) {
        this.jwt = jwt;
        this.admin = admin;
    }
}
