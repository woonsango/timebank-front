package com.capstone.timepay.controller.admin.response;


import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.service.admin.dto.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController("response.AdminController")
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    // 어드민 리스트 반환
    @GetMapping("")
    public ResponseEntity<List<Admin>> getAdmins() {
        List<Admin> adminList = this.adminService.getList();
        return new ResponseEntity<>(adminList, HttpStatus.OK);
    }
}
