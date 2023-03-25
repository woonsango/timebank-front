package com.capstone.timepay.controller.admin;

import com.capstone.timepay.controller.admin.response.comment.CommentResponse;
import com.capstone.timepay.controller.admin.response.report.ReportResponse;
import com.capstone.timepay.service.admin.ReportManageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admins/reports")
public class ReportManageController {

    private final ReportManageService reportManageService;

    @ApiOperation(value = "전체 댓글 리스트 조회")
    @GetMapping("/main")
    public ResponseEntity<?> main(){

        List<ReportResponse> responses = reportManageService.showAllReports();

        return ResponseEntity.ok(responses);
    }
}
