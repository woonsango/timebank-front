package com.capstone.timepay.controller.organization;

import com.capstone.timepay.controller.admin.request.auth.AdminRegisterRequest;
import com.capstone.timepay.controller.organization.request.OrgaUpdateRequest;
import com.capstone.timepay.controller.organization.request.OrgaUserSignUpRequest;
import com.capstone.timepay.service.organization.OrganizationUserService;
import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/organizations")
public class OrganizationUserController {

    private final OrganizationUserService organizationUserService;

    @PostMapping(value= "/register",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "기관 회원 회원가입")
    public ResponseEntity<Map<String, Object>> createOrganizationUser(@RequestPart OrgaUserSignUpRequest request,
                                                                      @RequestPart(required = false) MultipartFile image,
                                                                      @RequestPart(required = false) MultipartFile certification) throws IOException, FirebaseAuthException {
        Map<String, Object> result = organizationUserService.create(request.toServiceDto(), image, certification);

        if ((Boolean) result.get("success")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping(value= "/update",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "기관 회원 정보 수정")
    public ResponseEntity<?> updateOrganizationInfo(@RequestPart OrgaUpdateRequest request,
                                                    @RequestPart(required = false) MultipartFile image,
                                                    @RequestPart(required = false) MultipartFile certification,
                                                    Principal principal) throws IOException, FirebaseAuthException {

        organizationUserService.updateInfo(request, image, certification, principal.getName());

        return ResponseEntity.ok("수정되었습니다.");
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "기관 회원 회원 탈퇴")
    public ResponseEntity<Map<String, Object>> deleteOrganizationUser(Principal principal) {
        Map<String, Object> result = organizationUserService.delete(principal.getName());

        if ((Boolean) result.get("success")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
