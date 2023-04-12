package com.capstone.timepay.controller.admin;

import com.capstone.timepay.controller.admin.request.comment.CommentHideRequest;
import com.capstone.timepay.controller.admin.response.comment.CommentResponse;
import com.capstone.timepay.controller.admin.response.inquiry.InquiryResponse;
import com.capstone.timepay.service.admin.CommentManageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/comments")
public class CommentManageController {

    private final CommentManageService commentManageService;

    @ApiOperation(value = "전체 댓글 리스트 조회")
    @GetMapping("/main")
    public ResponseEntity<?> main(@RequestParam(defaultValue = "0") int pageIndex,
                                  @RequestParam(defaultValue = "50") int pageSize){

        Page<CommentResponse> responses = commentManageService.showAllComments(pageIndex, pageSize);

        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "뎃글 숨김")
    @PatchMapping(value = "/hide", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> hideComment(@RequestBody CommentHideRequest request){

        commentManageService.hideComment(request.toServiceDto());

        return ResponseEntity.ok("숨김 처리 되었습니다.");
    }

}
