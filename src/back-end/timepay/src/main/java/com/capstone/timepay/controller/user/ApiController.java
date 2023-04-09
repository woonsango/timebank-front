package com.capstone.timepay.controller.user;

import com.capstone.timepay.controller.user.request.RequestDTO;
import com.capstone.timepay.controller.user.response.ResponseDTO;
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
    /* ȸ������ ��ư Ŭ���ϸ� �����Ͱ� Post�� format�������� �Ѿ�� */
    /* json ������ �����͸� �޾Ƽ� createUserService�� �Ѱ��� */
    /* īī�� �����Ϳ� ��� ��Ī������ ���� �ʿ� */

    @PostMapping("/create")
    @ApiOperation(value="���� ������ ����",notes = "uid�� �̿��Ͽ� ���� ���̺�� ���� ������ ���̺��� �����ϰ�, DB�� �����͸� �����մϴ�.")
    public ResponseEntity postKakaoData(@ModelAttribute RequestDTO requestData, @RequestPart MultipartFile image) throws Exception {

        requestData.setImageUrl(userInfoService.imageUpload(image));
        userInfoService.createUserInfo(requestData);

        return ResponseEntity.ok(requestData);
    }

    @GetMapping("/get/{uid}")
    @ApiOperation(value="���� ������ ��ȸ",notes = "�ּҷ� uid�� �޾� �ش��ϴ� ���� ������ ��ȸ�մϴ�.")
    public ResponseEntity getUserInfo(@PathVariable Long uid){
        ResponseDTO responseData = userInfoService.getUserInfo(uid);
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/update")
    @ApiOperation(value="���� ������ ����",notes = "uid�� �̿��Ͽ� ������ �����ϰ� �����͸� �����մϴ�.")
    public ResponseEntity putUserInfo(@ModelAttribute RequestDTO requestData, @RequestPart MultipartFile image) throws Exception{
        requestData.setImageUrl(userInfoService.imageUpload(image));
        userInfoService.updateUserInfo(requestData);
        return ResponseEntity.ok(requestData);
    }

    @DeleteMapping("/delete/{uid}")
    @ApiOperation(value="���� ������ ����(ȸ��Ż��)",notes = "�ּҷ� uid�� �޾� �ش��ϴ� ���� ������ �����մϴ�.")
    public ResponseEntity deleteUserInfo(@PathVariable Long uid) {
        userInfoService.deleteUserInfo(uid);
        return ResponseEntity.ok(uid + " Delete Success");
    }
}
