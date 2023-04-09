package com.capstone.timepay.controller.user;

import com.capstone.timepay.controller.user.request.RequestDTO;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.service.user.service.UserInfoService;
import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class ApiController {
    private final UserInfoService userInfoService;
    /* 회원가입 버튼 클릭하면 데이터가 Post로 format형식으로 넘어옴 */
    /* json 형식의 데이터를 받아서 createUserService로 넘겨줌 */
    /* 카카오 데이터와 어떻게 매칭해줄지 생각 필요 */

    @PostMapping("/create")
    @ApiOperation(value="유저 데이터 생성",notes = "uid를 이용하여 유저 테이블과 유저 프로필 테이블을 매핑하고, DB에 데이터를 생성합니다.")
    public ResponseEntity<?> postKakaoData(@ModelAttribute RequestDTO requestData, @RequestPart MultipartFile image) throws Exception {

        requestData.setImageUrl(userInfoService.imageUpload(image));
        userInfoService.createUserInfo(requestData);

        return ResponseEntity.ok(requestData);
    }

    @GetMapping("/get/{uid}")
    @ApiOperation(value="유저 데이터 조회",notes = "주소로 uid를 받아 해당하는 유저 정보를 조회합니다.")
    public ResponseEntity<?> getUserInfo(@PathVariable Long uid){
        RequestDTO requestData = userInfoService.getUserInfo(uid);
        return ResponseEntity.ok(requestData);
    }

    @PutMapping("/update")
    @ApiOperation(value="유저 데이터 수정",notes = "uid를 이용하여 유저를 매핑하고 데이터를 수정합니다.")
    public ResponseEntity<?> putUserInfo(@ModelAttribute RequestDTO requestData, @RequestPart MultipartFile image) throws Exception{
        requestData.setImageUrl(userInfoService.imageUpload(image));
        userInfoService.updateUserInfo(requestData);
        return ResponseEntity.ok(requestData);
    }

    @DeleteMapping("/delete/{uid}")
    @ApiOperation(value="유저 데이터 삭제(회원탈퇴)",notes = "주소로 uid를 받아 해당하는 유저 정보를 삭제합니다.")
    public ResponseEntity<?> deleteUserInfo(@PathVariable Long uid) {
        userInfoService.deleteUserInfo(uid);
        return ResponseEntity.ok(uid + " Delete Success");
    }
}
