package com.capstone.timepay.controller.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateUserController {

    /* 회원가입 버튼 클릭하면 데이터가 Post로 json 형식으로 넘어옴 */
    /* json 형식의 데이터를 받아서 UserSignUpService로 넘겨줌 */
    /* 카카오 데이터와 어떻게 매칭해줄지 생각 필요 */
    @PostMapping("user/create")
    public void createUser(){

    }
}
