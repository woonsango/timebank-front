package com.capstone.timepay.controller.admin;

import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.service.admin.AdminService;
import com.capstone.timepay.service.admin.dto.ChangePasswordDTO;
import com.capstone.timepay.service.admin.dto.PostAdminDTO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("")
    @ApiOperation(value = "어드민 생성")
    public ResponseEntity<Map<String, Object>> createAdmin(@RequestBody PostAdminDTO dto) {
        Map<String, Object> result = this.adminService.create(dto);

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

    @PostMapping("/password/change")
    @ApiOperation(value = "비밀번호 변경 api")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody ChangePasswordDTO dto,
                                                              Principal principal) {

        Map<String, Object> result = adminService.changePassword(dto, principal.getName());
        return ResponseEntity.ok(result);
    }

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

    @GetMapping("/info")
    @ApiOperation("현재 로그인 되어있는 어드민 조회")
    public ResponseEntity<Admin> getAdmin(Principal principal) {
        return new ResponseEntity<>(this.adminService.getOne(principal.getName()), HttpStatus.OK);
    }
}
