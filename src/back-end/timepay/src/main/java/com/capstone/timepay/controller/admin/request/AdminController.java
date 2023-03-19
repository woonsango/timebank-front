package com.capstone.timepay.controller.admin.request;

import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.service.admin.dto.AdminService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController("request.AdminController")
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("")
    @ApiOperation(value = "어드민 생성")
    public ResponseEntity<Map<String, Object>> createAdmin(@RequestBody Admin admin) {
        Map<String, Object> result = this.adminService.create(admin);

        if ((Boolean) result.get("success")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{adminId}")
    @ApiOperation(value = "어드민 삭제")
    public ResponseEntity<Map<String, Object>> deleteAdmin(@PathVariable Long adminId) {
        Map<String, Object> result = adminService.delete(adminId);

        if ((Boolean) result.get("success")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
