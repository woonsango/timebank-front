package com.capstone.timepay.controller.admin;

import com.capstone.timepay.controller.admin.request.userManage.UserInfoUpdateRequest;
import com.capstone.timepay.controller.admin.response.userManage.ActivityListDto;
import com.capstone.timepay.controller.admin.response.userManage.MainResponse;
import com.capstone.timepay.controller.admin.response.userManage.UserProfileResponse;
import com.capstone.timepay.service.admin.UserManageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/normal-user-management")
public class UserManageController {

    private final UserManageService userManageService;

    @ApiOperation(value = "전체 유저 정보 리스트 조회")
    @GetMapping("/main")
    public ResponseEntity<?> main(@RequestParam(defaultValue = "0") int pageIndex,
                                  @RequestParam(defaultValue = "50") int pageSize){

        Page<MainResponse> responses = userManageService.showAllUserList(pageIndex, pageSize);

        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "쿼리를 통한 유저 필터링 : 쿼리 ( name / email / nickname )")
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) Long userId,
                                    @RequestParam(required = false) String query,
                                    @RequestParam(required = false) String value,
                                    @RequestParam(defaultValue = "0") int pageIndex,
                                    @RequestParam(defaultValue = "50") int pageSize){

        Page<MainResponse> responses = userManageService.showAllUserBySearch(userId, query, value, pageIndex, pageSize);

        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "유저 활동 목록 조회")
    @GetMapping("/activity-list")
    public ResponseEntity<?> showActivityList(@RequestParam Long userId,
                                              @RequestParam(defaultValue = "0") int pageIndex,
                                              @RequestParam(defaultValue = "50") int pageSize){

        ActivityListDto activityListDto = userManageService.getActivityList(userId,pageIndex,pageSize);

        return ResponseEntity.ok(activityListDto);
    }

    @ApiOperation(value = "유저 프로필 이미지 조회")
    @GetMapping("/profile")
    public ResponseEntity<?> showUserProfile(@RequestParam Long userId){

        UserProfileResponse response = userManageService.showUserProfile(userId);

        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "유저 정보 수정")
    @PatchMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateUserInfo(@Valid @RequestBody UserInfoUpdateRequest request){

        userManageService.updateUserInfo(request.toServiceDto());

        return ResponseEntity.ok("수정되었습니다.");
    }

    @ApiOperation(value = "유저 정보 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId){

        userManageService.deleteUser(userId);

        return ResponseEntity.ok("삭제되었습니다.");
    }

    @ApiOperation(value = "유저 블랙리스트 등록")
    @PostMapping("/blacklist")
    public ResponseEntity<?> registerBlacklist(@RequestParam Long userId){

        userManageService.registerBlacklist(userId);

        return ResponseEntity.ok("등록되었습니다.");
    }


}
