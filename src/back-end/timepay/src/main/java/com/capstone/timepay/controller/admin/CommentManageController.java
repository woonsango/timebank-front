package com.capstone.timepay.controller.admin;

import com.capstone.timepay.controller.admin.response.comment.CommentResponse;
import com.capstone.timepay.controller.admin.response.inquiry.InquiryResponse;
import com.capstone.timepay.service.admin.CommentManageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<?> main(){

        List<CommentResponse> responses = commentManageService.showAllComments();

        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "뎃글 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestParam Long commentId){

        commentManageService.deleteComment(commentId);

        return ResponseEntity.ok("삭제되었습니다.");
    }

}
