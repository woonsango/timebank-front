package com.capstone.timepay.controller.admin;

import com.capstone.timepay.controller.admin.request.UserInfoUpdateRequest;
import com.capstone.timepay.controller.admin.response.userManage.MainResponse;
import com.capstone.timepay.controller.admin.response.userManage.UserProfileResponse;
import com.capstone.timepay.service.admin.UserManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/user-management")
public class UserManageController {

    private final UserManageService userManageService;

    @GetMapping("/main")
    public ResponseEntity<?> main(){

        List<MainResponse> responses = userManageService.showAllUserList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam Long userId, @RequestParam String query){

        List<MainResponse> responses = new ArrayList<>();
        if(query.equals("name")) responses = userManageService.showAllUserListByName(userId, query);
        else if(query.equals("email")) responses = userManageService.showAllUserListByEmail(userId, query);
        else if(query.equals("nickname")) responses = userManageService.showAllUserListByNickname(userId, query);
        else throw new IllegalArgumentException("잘못된 요청입니다.");

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/activity-list")
    public ResponseEntity<?> showActivityList(){

        return ResponseEntity.ok("");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> showUserProfile(@RequestParam Long userId){

        UserProfileResponse response = userManageService.showUserProfile(userId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateUserInfo(@Valid @RequestBody UserInfoUpdateRequest request){

        userManageService.updateUserInfo(request.toServiceDto());

        return ResponseEntity.ok("수정되었습니다.");
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
