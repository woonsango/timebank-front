package com.capstone.timepay.domain.user.model;

import lombok.Data;


@Data
public class AuthenticationRequest {
    private String name;
    private String password;

    public AuthenticationRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
