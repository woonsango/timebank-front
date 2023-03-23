package com.capstone.timepay.controller.user;

import com.capstone.timepay.controller.user.request.RequestDTO;
import com.capstone.timepay.service.user.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ApiController {
    private final KakaoLoginService kakaoLoginService;
    /* 회원가입 버튼 클릭하면 데이터가 Post로 json 형식으로 넘어옴 */
    /* json 형식의 데이터를 받아서 UserSignUpService로 넘겨줌 */
    /* 카카오 데이터와 어떻게 매칭해줄지 생각 필요 */

    /***
     * Get으로 프론트에서 데이터를 받는 것이 맞는 것 같음
    @PostMapping("kakao/uid")
    public void postKakaoData(@RequestBody Map<String, Object> requestData){

        requestData.forEach((key, value) -> {
            System.out.println("key : " + key); // uid
            System.out.println("value : " + value); // 이게 필요한 실 데이터 값
        });
    }
     *

    @GetMapping("kakao/uid")
    public RequestDTO postKakaoData(Long uid){
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setUid(uid);
        return requestDTO;
    }
     ***/
}
