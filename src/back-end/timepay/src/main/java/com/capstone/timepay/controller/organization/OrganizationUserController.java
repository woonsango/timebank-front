package com.capstone.timepay.controller.organization;

import com.capstone.timepay.controller.admin.request.auth.AdminRegisterRequest;
import com.capstone.timepay.controller.organization.request.OrgaUserSignUpRequest;
import com.capstone.timepay.service.organization.OrganizationUserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/organizations")
public class OrganizationUserController {

    private final OrganizationUserService organizationUserService;

    @PostMapping("/register")
    @ApiOperation(value = "기관 회원 회원가입")
    public ResponseEntity<Map<String, Object>> createOrganizationUser(@RequestBody OrgaUserSignUpRequest request) {
        Map<String, Object> result = organizationUserService.create(request.toServiceDto());

        if ((Boolean) result.get("success")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
