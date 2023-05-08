package com.capstone.timepay.controller.admin;

import com.capstone.timepay.controller.admin.request.comment.CommentHideRequest;
import com.capstone.timepay.controller.admin.request.comment.CommentSearchRequest;
import com.capstone.timepay.controller.admin.response.comment.CommentResponse;
import com.capstone.timepay.service.admin.CommentManageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "댓글 검색 조회")
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String query, @RequestParam String value){

        Page<CommentResponse> responses = commentManageService.showCommentsBySearch(query,value);

        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "뎃글 숨김")
    @PatchMapping(value = "/hide", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> hideComment(@RequestBody CommentHideRequest request){

        commentManageService.hideComment(request.toServiceDto());

        return ResponseEntity.ok("숨김 처리 되었습니다.");
    }

}
