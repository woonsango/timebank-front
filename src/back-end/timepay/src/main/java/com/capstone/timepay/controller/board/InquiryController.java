package com.capstone.timepay.controller.board;

import com.capstone.timepay.domain.inquiry.Inquiry;
import com.capstone.timepay.service.board.dto.InquiryDTO;
import com.capstone.timepay.service.board.service.InquiryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inquiry-boards")
public class InquiryController {

    private final InquiryService inquiryService;

    @ApiOperation(value = "문의게시글 작성")
    @PostMapping("/write")
    public ResponseEntity createInquiry(@RequestBody InquiryDTO inquiryDTO,
                                        Principal principal) {
        return new ResponseEntity<>(inquiryService.createInquiry(inquiryDTO, principal.getName()), HttpStatus.CREATED);
    }

    @ApiOperation(value = "문의게시글 조회")
    @GetMapping("")
    public ResponseEntity<Page<InquiryDTO>> getInquiries(@RequestParam(value = "pagingIndex", defaultValue = "0") int pagingIndex,
                                                         @RequestParam(value = "pagingSize", defaultValue = "50") int pagingSize) {
        Page<InquiryDTO> inquiries = inquiryService.getAllInquiries(pagingIndex, pagingSize);
        return new ResponseEntity<>(inquiries, HttpStatus.OK);
    }

    @ApiOperation(value = "내가 쓴 문의게시글 조회")
    @GetMapping("/my-page")
    public ResponseEntity getMyInquiries(@RequestParam(value = "pagingIndex", defaultValue = "0") int pagingIndex,
                                                           @RequestParam(value = "pagingSize", defaultValue = "50") int pagingSize,
                                                           Principal principal) {
        Page<InquiryDTO> inquiries = inquiryService.getMyInquiries(pagingIndex, pagingSize, principal);
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