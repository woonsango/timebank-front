package com.capstone.timepay.controller.user.response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class KaKaoLoginController {

    @GetMapping("/login")
    public void kakaoCallback(@RequestParam String code)  {
        System.out.println(code);
    }

}
