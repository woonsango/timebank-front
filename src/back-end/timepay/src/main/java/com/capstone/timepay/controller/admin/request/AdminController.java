package com.capstone.timepay.controller.admin.request;

import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.service.admin.dto.AdminService;
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

    // 어드민 생성, 성공 여부 반환
    @PostMapping("")
    public ResponseEntity<Boolean> createAdmin(@RequestBody Admin admin) {
        boolean success = this.adminService.create(admin);
        List<Admin> adminList = this.adminService.getList();
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    // 어드민 삭제, 성공 여부 반환
    @DeleteMapping("/{adminId}")
    public ResponseEntity<Map<String, Object>> deleteAdmin(@PathVariable Long adminId) {
        Map<String, Object> result = adminService.delete(adminId);

        if ((Boolean) result.get("success")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
