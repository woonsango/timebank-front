package com.capstone.timepay.domain.user.model;

import com.capstone.timepay.domain.user.User;
import lombok.Getter;

import java.util.List;

@Getter
public class AuthenticationResponse {
    private final String jwt;
    private final List<String> user;

    public AuthenticationResponse(String jwt, List<String> user) {
        this.jwt = jwt;
        this.user = user;
    }
}
