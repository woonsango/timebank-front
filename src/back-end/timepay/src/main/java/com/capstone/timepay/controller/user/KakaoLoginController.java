package com.capstone.timepay.controller.user;

import com.capstone.timepay.service.user.service.KakaoLoginService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class KakaoLoginController {
    private final KakaoLoginService kakaoLoginService;

    /* 카카오 로그인 */
    @GetMapping("/login")
    @ApiOperation(value="카카오 간편 로그인 콜백 함수",notes = "리다이렉션 URI로 실행되는 함수입니다. 따로 사용 X")
    public ResponseEntity kakaoCallback(@RequestParam String code) {
        String access_Token = kakaoLoginService.getKaKaoAccessToken(code);
        Long uid = kakaoLoginService.createKakaoUser(access_Token);

        /* DB 작성 완료 후, uid 보내기 */;
        return ResponseEntity.ok(uid);
    }
}
