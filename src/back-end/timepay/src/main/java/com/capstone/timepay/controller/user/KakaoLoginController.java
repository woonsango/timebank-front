package com.capstone.timepay.controller.user;

import com.capstone.timepay.service.user.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class KakaoLoginController {
    private final KakaoLoginService kakaoLoginService;
    /* 카카오 로그인 */

    @ResponseBody
    @GetMapping("/login")
    public void kakaoCallback(@RequestParam String code) {
        String access_Token = kakaoLoginService.getKaKaoAccessToken(code);
        kakaoLoginService.createKakaoUser(access_Token);
    }


}
