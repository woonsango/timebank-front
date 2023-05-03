package com.capstone.timepay.controller.user;

import com.capstone.timepay.controller.user.response.CertificationListResponse;
import com.capstone.timepay.service.organization.OrganizationManageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/mypage")
public class UserMypageController {

    private OrganizationManageService organizationManageService;

    @ApiOperation(value = "봉사활동 인증서 내역")
    @GetMapping("/certification")
    public ResponseEntity<?> getCertificationList(Principal principal,
                                                  @RequestParam(defaultValue = "0") int pageIndex,
                                                  @RequestParam(defaultValue = "10") int pageSize){

        CertificationListResponse response = organizationManageService.getCertificationList(principal.getName(),pageIndex,pageSize);

        return ResponseEntity.ok(response);
    }
}
