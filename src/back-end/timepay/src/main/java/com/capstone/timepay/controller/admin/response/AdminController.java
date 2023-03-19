package com.capstone.timepay.controller.admin.response;


import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.service.admin.AdminService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController("response.AdminController")
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("")
    @ApiOperation("전체 어드민 리스트 조회")
    public ResponseEntity<List<Admin>> getAdmins() {
        List<Admin> adminList = this.adminService.getList();
        return new ResponseEntity<>(adminList, HttpStatus.OK);
    }

    @GetMapping("/{adminId}")
    @ApiOperation("특정 id값 어드민 조회")
    public ResponseEntity<Admin> getAdmin(@PathVariable Long adminId) {
        return new ResponseEntity<>(this.adminService.getOne(adminId).get(), HttpStatus.OK);
    }
}
