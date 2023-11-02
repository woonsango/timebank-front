package com.capstone.timepay.controller.admin;

import com.capstone.timepay.controller.admin.response.organization.OrganizationMain;
import com.capstone.timepay.service.admin.AdminOrgaManageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/organization-user-management")
public class AdminOrgaManageController {

    private final AdminOrgaManageService organizationManageService;

    @ApiOperation(value = "전체 기관 유저 정보 리스트 조회")
    @GetMapping("/main")
    public ResponseEntity<?> main(@RequestParam(defaultValue = "0") int pagingIndex,
                                  @RequestParam(defaultValue = "50") int pagingSize){

        Page<OrganizationMain> responses = organizationManageService.showAllUserList(pagingIndex, pagingSize);

        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "기관 회원 봉사활동 자격 부여")
    @PatchMapping("/authority")
    public ResponseEntity<?> giveAuthority(@RequestParam Long userId){

        organizationManageService.giveAuthority(userId);

        return ResponseEntity.ok("완료되었습니다.");
    }

    @ApiOperation(value = "기관 회원 페널티 부여")
    @PatchMapping("/penalty")
    public ResponseEntity<?> givePenalty(@RequestParam Long userId){

        organizationManageService.givePenalty(userId);

        return ResponseEntity.ok("완료되었습니다.");
    }

    @ApiOperation(value = "전체 기관 유저 정보 리스트 검색 조회")
    @GetMapping("/search")
    public ResponseEntity<?> main(@RequestParam(required = false) String query,
                                  @RequestParam(required = false) String value,
                                  @RequestParam(required = false) String volunteer){

        Page<OrganizationMain> responses = organizationManageService.showAllUserListBySearch(query, value, volunteer);

        return ResponseEntity.ok(responses);
    }


}
