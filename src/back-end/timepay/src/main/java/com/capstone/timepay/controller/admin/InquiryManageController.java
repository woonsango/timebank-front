package com.capstone.timepay.controller.admin;

import com.capstone.timepay.controller.admin.response.inquiry.InquiryResponse;
import com.capstone.timepay.service.admin.InquiryManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/inquiry")
public class InquiryManageController {

    private final InquiryManagerService inquiryManagerService;

    @GetMapping("/main")
    public ResponseEntity<?> main(){

        List<InquiryResponse> responses = inquiryManagerService.showAllInquiries();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchInquiries(@RequestParam String state, @RequestParam String category, @RequestParam String writer, @RequestParam String title){

        // 넘겨줄 쿼리가 없으면 "all"을 넘겨줄 것.
        List<InquiryResponse> responses = inquiryManagerService.searchInquiriesByQuery(state, category, writer, title);

        return ResponseEntity.ok(responses);
    }



}
