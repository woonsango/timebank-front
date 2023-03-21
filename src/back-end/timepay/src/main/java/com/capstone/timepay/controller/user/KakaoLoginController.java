package com.capstone.timepay.controller.user;

import com.capstone.timepay.service.user.KakaoLoginService;
import org.springframework.web.bind.annotation.*;


@RestController
public class KakaoLoginController {

    /* 카카오 로그인 */

    @ResponseBody
    @GetMapping("/login")
    public void kakaoCallback(@RequestParam String code) {
        String access_Token = KakaoLoginService.getKaKaoAccessToken(code);
        KakaoLoginService.createKakaoUser(access_Token);
    }

    /* 회원가입 버튼 클릭하면 데이터가 Post로 json 형식으로 넘어옴 */
    /* json 형식의 데이터를 받아서 UserSignUpService로 넘겨줌 */
    @PostMapping("user/create")
    public void createUser(){
    }
}
