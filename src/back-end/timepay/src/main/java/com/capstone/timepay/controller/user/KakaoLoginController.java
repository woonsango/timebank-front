package com.capstone.timepay.controller.user;

import com.capstone.timepay.controller.user.response.KakaoLoginDTO;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.model.AuthenticationResponse;
import com.capstone.timepay.service.user.service.KakaoLoginService;
import com.capstone.timepay.service.user.service.UserDetailService;
import com.capstone.timepay.utility.JwtUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class KakaoLoginController {
    private final KakaoLoginService kakaoLoginService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailService userDetailService;

    /* 카카오 로그인 */
    @GetMapping ("/oauth/redirect/kakao")
    @ApiOperation(value = "카카오 간편 로그인", notes = "?code={인가코드}를 받아 작동합니다.")
    public ResponseEntity<?> kakaoCallback(@RequestParam("code") String code) throws Exception {
        String access_Token = kakaoLoginService.getKaKaoAccessToken(code);
        User user = kakaoLoginService.createKakaoUser(access_Token);

        if(user.isSignUp()) {
            final UserDetails userDetails = userDetailService.loadUserByUsername(user.getEmail());
            final String token = jwtUtils.createToken(userDetails.getUsername(), user.getRoles());
            return ResponseEntity.ok(new AuthenticationResponse(token, user.getRoles(), user.isSignUp()));
        }

        return ResponseEntity.ok(new KakaoLoginDTO(user.isSignUp(), user.getUserId()));
    }
}
