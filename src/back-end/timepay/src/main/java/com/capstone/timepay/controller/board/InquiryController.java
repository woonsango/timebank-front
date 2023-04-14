package com.capstone.timepay.controller.board;

import com.capstone.timepay.domain.inquiry.Inquiry;
import com.capstone.timepay.service.board.dto.InquiryDTO;
import com.capstone.timepay.service.board.service.InquiryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inquiry-boards")
public class InquiryController {

    private final InquiryService inquiryService;

    @ApiOperation(value = "문의게시글 작성")
    @PostMapping("/write")
    public ResponseEntity<Inquiry> createInquiry(@RequestBody InquiryDTO inquiryDTO) {
        Inquiry inquiry = inquiryService.createInquiry(inquiryDTO);
        return new ResponseEntity<>(inquiry, HttpStatus.CREATED);
    }

    @ApiOperation(value = "문의게시글 조회")
    @GetMapping("")
    public ResponseEntity<List<Inquiry>> getInquiries() {
        List<Inquiry> inquiries = inquiryService.getAllInquiries();
        return new ResponseEntity<>(inquiries, HttpStatus.OK);
    }

    @ApiOperation(value = "문의게시글 수정")
    @PutMapping("/{inquiryId}")
    public ResponseEntity<Inquiry> updateInquiry(@PathVariable Long inquiryId,
                                                 @RequestBody InquiryDTO inquiryDTO) {
        Inquiry updatedInquiry = inquiryService.updateInquiry(inquiryId, inquiryDTO);
        if (updatedInquiry != null) {
            return new ResponseEntity<>(updatedInquiry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "문의게시글 삭제")
    @DeleteMapping("/{inquiryId}")
    public ResponseEntity<Void> deleteInquiry(@PathVariable Long inquiryId) {
        inquiryService.deleteInquiry(inquiryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}