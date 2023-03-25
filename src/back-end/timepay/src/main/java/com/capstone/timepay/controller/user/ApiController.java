package com.capstone.timepay.controller.user;

import com.capstone.timepay.controller.user.request.RequestDTO;
import com.capstone.timepay.service.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class ApiController {
    private final UserInfoService userInfoService;
    /* 회원가입 버튼 클릭하면 데이터가 Post로 json형식으로 넘어옴 */
    /* json 형식의 데이터를 받아서 createUserService로 넘겨줌 */
    /* 카카오 데이터와 어떻게 매칭해줄지 생각 필요 */

    @PostMapping("/create")
    public ResponseEntity postKakaoData(@RequestBody RequestDTO requestData){
        userInfoService.createUserInfo(requestData);
        return ResponseEntity.ok(requestData);
    }

    @PutMapping("/update")
    public ResponseEntity putUserInfo(@RequestBody RequestDTO requestData){
        userInfoService.updateUserInfo(requestData);
        return ResponseEntity.ok(requestData);
    }
}
