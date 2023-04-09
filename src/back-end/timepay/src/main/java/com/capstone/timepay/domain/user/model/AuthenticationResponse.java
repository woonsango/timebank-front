package com.capstone.timepay.domain.user.model;

import com.capstone.timepay.domain.user.User;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class AuthenticationResponse {
    private final String jwt;
    private final UserDetails user;

    public AuthenticationResponse(String jwt, UserDetails user) {
        this.jwt = jwt;
        this.user = user;
    }
}
