package com.capstone.timepay.controller.admin.response;


import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.service.admin.AdminService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController("response.AdminController")
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("")
    @ApiOperation("전체 어드민 리스트 조회")
    public ResponseEntity<Page<Admin>> getAdmins(
            @RequestParam(value = "pagingIndex", defaultValue = "0") int pagingIndex,
            @RequestParam(value = "pagingSize", defaultValue = "50") int pagingSize) {

        Page<Admin> paging = this.adminService.getList(pagingIndex, pagingSize);
        if (paging.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(paging, HttpStatus.OK);
    }

    @GetMapping("/{adminId}")
    @ApiOperation("특정 id값 어드민 조회")
    public ResponseEntity<Admin> getAdmin(@PathVariable Long adminId) {
        return new ResponseEntity<>(this.adminService.getOne(adminId).get(), HttpStatus.OK);
    }
}
