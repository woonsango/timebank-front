package com.capstone.timepay.controller.user.response;

import com.capstone.timepay.service.user.KakaoLoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class KakaoLoginControllerRequest {

    @GetMapping("/login")
    public void kakaoCallback(@RequestParam String code)  {
        String access_Token = KakaoLoginService.getKaKaoAccessToken(code);
        KakaoLoginService.createKakaoUser(access_Token);
    }
}
