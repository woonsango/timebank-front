package com.capstone.timepay.domain.user.model;

import com.capstone.timepay.domain.user.User;
import lombok.Getter;

import java.util.List;

@Getter
public class AuthenticationResponse {
    private final String jwt;
    private final List<String> user;
    private final Boolean signUp;

    public AuthenticationResponse(String jwt, List<String> user, Boolean signUp) {
        this.jwt = jwt;
        this.user = user;
        this.signUp = signUp;
    }
}
