package com.capstone.timepay.controller.user;

import com.capstone.timepay.controller.user.response.CertificationListResponse;
import com.capstone.timepay.controller.user.response.GetResponseDTO;
import com.capstone.timepay.service.organization.OrganizationManageService;
import com.capstone.timepay.service.user.service.UserInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/mypage")
public class UserMypageController {

    private final OrganizationManageService organizationManageService;
    private final UserInfoService userInfoService;

    @Transactional(readOnly = true)
    @GetMapping("")
    @ApiOperation(value="마이페이지 조회",notes = "JWT 토큰에 해당하는 유저의 프로필 정보를 조회합니다.(마이페이지)")
    public ResponseEntity<?> getMyInfo(@RequestParam(defaultValue = "0") int pageIndex,
                                       @RequestParam(defaultValue = "50") int pageSize){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        GetResponseDTO responseData = userInfoService.getMyInfo(auth, pageIndex, pageSize);
        return ResponseEntity.ok(responseData);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    @ApiOperation(value="유저 데이터 조회",notes = "주소로 id를 받아 해당하는 유저 정보를 조회합니다.")
    public ResponseEntity<?> getUserInfo(@PathVariable Long id, @RequestParam(required = false) String boardType,
                                         @RequestParam(required = false) String boardStatus,
                                         @RequestParam(required = false) String commentType,
                                         @RequestParam(defaultValue = "0") int pageIndex,
                                         @RequestParam(defaultValue = "50") int pageSize){
        GetResponseDTO responseData = userInfoService.getUserInfo(id, boardType, boardStatus, commentType, pageIndex, pageSize);
        return ResponseEntity.ok(responseData);
    }

    @ApiOperation(value = "봉사활동 인증서 내역")
    @GetMapping("/certification")
    public ResponseEntity<?> getCertificationList(Principal principal,
                                                  @RequestParam(defaultValue = "0") int pageIndex,
                                                  @RequestParam(defaultValue = "10") int pageSize){

        CertificationListResponse response = organizationManageService.getCertificationList(principal.getName(),pageIndex,pageSize);

        return ResponseEntity.ok(response);
    }
}
