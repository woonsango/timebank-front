package com.capstone.timepay.controller.admin;

import com.capstone.timepay.controller.admin.response.MainResponse;
import com.capstone.timepay.service.admin.AdminUserManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/user-management")
public class AdminUserManageController {

    private final AdminUserManageService adminUserManageService;

    @GetMapping("/main")
    public ResponseEntity<?> main(){

        List<MainResponse> responses = adminUserManageService.showAllUserList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/activity-list")
    public ResponseEntity<?> showActivityList(){

        return ResponseEntity.ok("");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> showUserProfile(){

        return ResponseEntity.ok("");
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateUserInfo(){

        return ResponseEntity.ok("");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(){

        return ResponseEntity.ok("");
    }

    @PostMapping("/blacklist")
    public ResponseEntity<?> registerBlacklist(){

        return ResponseEntity.ok("");
    }

}
