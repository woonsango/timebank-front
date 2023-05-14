package com.capstone.timepay.controller.admin;

import com.capstone.timepay.controller.admin.request.inquiry.InquiryAnswerRequest;
import com.capstone.timepay.controller.admin.response.inquiry.InquiryDetailResponse;
import com.capstone.timepay.controller.admin.response.inquiry.InquiryResponse;
import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.service.admin.AdminService;
import com.capstone.timepay.service.admin.InquiryManagerService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/inquiry")
public class InquiryManageController {

    private final InquiryManagerService inquiryManagerService;
    private final AdminService adminService;

    @ApiOperation(value = "전체 문의 리스트 조회")
    @GetMapping("/main")
    public ResponseEntity<?> main(@RequestParam(defaultValue = "0") int pageIndex,
                                  @RequestParam(defaultValue = "50") int pageSize){

        Page<InquiryResponse> responses = inquiryManagerService.showAllInquiries(pageIndex, pageSize);

        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "쿼리를 통한 문의 필터링 : state, category 는 필수로 넘겨줄 것")
    @GetMapping("/search")
    public ResponseEntity<?> searchInquiries(@RequestParam String state,
                                             @RequestParam String category,
                                             @RequestParam(required = false) String writer,
                                             @RequestParam(required = false) String title,
                                             @RequestParam(defaultValue = "0") int pageIndex,
                                             @RequestParam(defaultValue = "50") int pageSize){

        Page<InquiryResponse> responses = inquiryManagerService.searchInquiriesByQuery(state, category, writer, title);
        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "세부 문의 정보 조회")
    @GetMapping("/detail")
    public ResponseEntity<?> showInquiryDetail(@RequestParam Long inquiryId){

        InquiryDetailResponse response = inquiryManagerService.showInquiryDetail(inquiryId);

        return ResponseEntity.ok(response);
    }
    @ApiOperation(value = "관리자 문의 답변 등록")
    @PostMapping(value = "/answer", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> answerInquiry(@Valid @RequestBody InquiryAnswerRequest request, Principal principal){

        Admin admin = adminService.getAdmin(principal.getName());
        inquiryManagerService.saveInquiryAnswer(request.toServiceDto(), admin);

        return ResponseEntity.ok("답변이 등록되었습니다.");
    }

}
