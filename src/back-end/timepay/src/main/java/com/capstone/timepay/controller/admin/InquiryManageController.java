package com.capstone.timepay.controller.admin;

import com.capstone.timepay.controller.admin.request.inquiry.InquiryAnswerRequest;
import com.capstone.timepay.controller.admin.response.inquiry.InquiryDetailResponse;
import com.capstone.timepay.controller.admin.response.inquiry.InquiryResponse;
import com.capstone.timepay.service.admin.InquiryManagerService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admins/inquiry")
public class InquiryManageController {

    private final InquiryManagerService inquiryManagerService;

    @ApiOperation(value = "전체 문의 리스트 조회")
    @GetMapping("/main")
    public ResponseEntity<?> main(){

        List<InquiryResponse> responses = inquiryManagerService.showAllInquiries();

        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "쿼리를 통한 문의 필터링 : 넘겨줄 쿼리가 없으면 all을 넘겨줄 것")
    @GetMapping("/search")
    public ResponseEntity<?> searchInquiries(@RequestParam String state, @RequestParam String category, @RequestParam String writer, @RequestParam String title){

        // 넘겨줄 쿼리가 없으면 "all"을 넘겨줄 것.
        List<InquiryResponse> responses = inquiryManagerService.searchInquiriesByQuery(state, category, writer, title);

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
    public ResponseEntity<?> answerInquiry(@Valid @RequestBody InquiryAnswerRequest request){

        inquiryManagerService.saveInquiryAnswer(request.toServiceDto());

        return ResponseEntity.ok("답변이 등록되었습니다.");
    }

}
