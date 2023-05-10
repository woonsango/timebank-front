package com.capstone.timepay.controller.organization;

import com.capstone.timepay.controller.organization.request.OrgaUpdateRequest;
import com.capstone.timepay.controller.organization.response.CertificatePublishResponse;
import com.capstone.timepay.service.organization.OrganizationManageService;
import com.capstone.timepay.service.organization.OrganizationUserService;
import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/organizations/mypage")
public class OrganizationManageController {

    private final OrganizationManageService organizationManageService;

    @GetMapping("/certificate")
    @ApiOperation(value = "특정 활동 게시글 봉사활동 인증서 발급")
    public ResponseEntity<?> updateOrganizationInfo(@RequestParam Long boardId){

        CertificatePublishResponse response = organizationManageService.publishCertificate(boardId);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/certificate/publish",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "특정 유저 게시글 봉사활동 인증서 발급")
    public ResponseEntity<?> updateOrganizationInfo(@RequestParam Long userId,
                                                    @RequestParam Long boardId,
                                                    @RequestPart MultipartFile certification) throws IOException, FirebaseAuthException {

        organizationManageService.publishUserCertificate(boardId,userId,certification);

        return ResponseEntity.ok("발급되었습니다.");
    }

}
