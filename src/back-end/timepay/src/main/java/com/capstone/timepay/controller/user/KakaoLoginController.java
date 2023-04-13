package com.capstone.timepay.controller.user;

import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.model.AuthenticationResponse;
import com.capstone.timepay.service.user.service.KakaoLoginService;
import com.capstone.timepay.service.user.service.UserDetailService;
import com.capstone.timepay.utility.JwtUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class KakaoLoginController {
    private final KakaoLoginService kakaoLoginService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailService userDetailService;

    /* 카카오 로그인 */
    @GetMapping("/login")
    @ApiOperation(value = "카카오 간편 로그인 콜백 함수", notes = "리다이렉션 URI로 실행되는 함수입니다. 따로 사용 X")
    public ResponseEntity<?> kakaoCallback(@RequestParam String code) throws Exception {
        String access_Token = kakaoLoginService.getKaKaoAccessToken(code);
        User user = kakaoLoginService.createKakaoUser(access_Token);

        if(user.isSignUp()) {
            final UserDetails userDetails = userDetailService.loadUserByUsername(user.getEmail());
            final String token = jwtUtils.createToken(userDetails.getUsername(), user.getRoles());
            return ResponseEntity.ok(new AuthenticationResponse(token, user.getRoles()));
        }

        return ResponseEntity.ok("회원가입 신청 완료\n" + user.getUid());
    }
}
