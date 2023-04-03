package com.capstone.timepay.domain.user.model;

import com.capstone.timepay.domain.user.User;
import lombok.Getter;

@Getter
public class AuthenticationResponse {
    private final String jwt;
    private final User user;

    public AuthenticationResponse(String jwt, User user) {
        this.jwt = jwt;
        this.user = user;
    }
}
