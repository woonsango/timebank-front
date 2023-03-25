package com.capstone.timepay.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {
    private String adminName;
    private String password;

    public AuthenticationRequest(String adminName, String password) {
        this.adminName = adminName;
        this.password = password;
    }
}
