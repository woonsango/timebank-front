package com.capstone.timepay.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationRequest {
    private String adminName;
    private String password;

    protected AuthenticationRequest(){}
}
