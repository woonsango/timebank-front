package com.capstone.timepay.controller.user;

import com.capstone.timepay.service.user.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class KakaoLoginController {
    private final KakaoLoginService kakaoLoginService;

    /* 카카오 로그인 */
    @GetMapping("/login")
    public ResponseEntity kakaoCallback(@RequestParam String code) {
        String access_Token = kakaoLoginService.getKaKaoAccessToken(code);
        Long uid = kakaoLoginService.createKakaoUser(access_Token);

        /* DB 작성 완료 후, uid 보내기 */;
        return ResponseEntity.ok(uid);
    }
}
