package com.capstone.timepay.controller.board;


import com.capstone.timepay.controller.board.request.ReportRequestDTO;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import com.capstone.timepay.service.board.dto.FreeBoardCommentDTO;
import com.capstone.timepay.service.board.service.FreeBoardCommentService;
import com.capstone.timepay.service.board.service.ReportService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/free-boards/comments")
public class FreeBoardCommentController {

    private final FreeBoardCommentService freeBoardCommentService;
    private final ReportService reportService;

    // 댓글 작성
    @ApiOperation(value = "자유게시글 댓글작성")
    @PostMapping("/write/{boardId}")
    public ResponseEntity writeFreeBoardComment(@PathVariable("boardId") Long boardId,
                                                @RequestBody FreeBoardCommentDTO freeBoardCommentDTO,
                                                Principal principal)
    {
        // TODO: 추후 User 정보는 세션을 통해 주고받도록 수정
        return new ResponseEntity(freeBoardCommentService.writeComment(boardId, freeBoardCommentDTO, principal.getName()), HttpStatus.OK);
    }

    @ApiOperation(value = "자유게시글 게시판의 댓글 불러오기")
    @GetMapping("/{boardId}")
    public ResponseEntity getFreeBoardComments(@PathVariable("boardId") Long boardId)
    {
        return new ResponseEntity(freeBoardCommentService.getComments(boardId), HttpStatus.OK);
    }

    @ApiOperation("자유게시판 댓글 삭제")
    @DeleteMapping("delete/{boardId}/{commentId}")
    public ResponseEntity<Map<String, Object>> deleteFreeBoardComment(
            @PathVariable("boardId") Long boardId,
            @PathVariable("commentId") Long commentId,
            Principal principal) {

        Map<String, Object> deleteMap = new HashMap<>();
        String userEmail = freeBoardCommentService.getEmail(commentId);

        try {
            FreeBoardComment freeBoardComment = freeBoardCommentService.getCommentId(commentId);

            if (freeBoardComment == null) {
                deleteMap.put("success", false);
                deleteMap.put("message", "해당 댓글을 찾을 수가 없습니다");
                return new ResponseEntity<>(deleteMap, HttpStatus.NOT_FOUND);
            }

            if (!userEmail.equals(principal.getName())) {
                deleteMap.put("success", false);
                deleteMap.put("message", "삭제 권한이 없습니다");
                return new ResponseEntity<Map<String, Object>>(deleteMap, HttpStatus.FORBIDDEN);
            }

            freeBoardCommentService.delete(commentId);
            deleteMap.put("success", true);
            return new ResponseEntity<Map<String, Object>>(deleteMap, HttpStatus.OK);

        } catch (Exception e) {
            deleteMap.put("success", false);
            deleteMap.put("message", "댓글 삭제 중 예기치 않은 오류가 발생했습니다");
            return new ResponseEntity<>(deleteMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/{boardId}/{commentId}/report")
    @ApiOperation(value = "신고 API", notes = "JWT 토큰으로 유저를 구분하여 신고 DB에 작성합니다.")
    public ResponseEntity<?> report(@PathVariable("boardId") Long boardId, @PathVariable("commentId") Long commentId, @RequestBody ReportRequestDTO requestDTO) {
        /* 현재 인증된 사용자의 인증 토큰을 가져온다.*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(reportService.reportComment(authentication, boardId, commentId, requestDTO, "일반댓글신고"));
    }

    @ApiOperation("자유게시판 댓글 수정")
    @PutMapping("update/{boardId}/{commentId}")
    public ResponseEntity<Map<String, Object>> updateFreeBoardComment(
            @PathVariable("boardId") Long boardId,
            @PathVariable("commentId") Long commentId,
            Principal principal,
            @RequestBody FreeBoardCommentDTO freeBoardCommentDTO) {

        Map<String, Object> updateMap = new HashMap<>();
        String userEmail = freeBoardCommentService.getEmail(commentId);

        try {
            FreeBoardComment freeBoardComment = freeBoardCommentService.getCommentId(commentId);

            if (freeBoardComment == null) {
                updateMap.put("success", false);
                updateMap.put("message", "해당 댓글을 찾을 수가 없습니다");
                return new ResponseEntity<>(updateMap, HttpStatus.NOT_FOUND);
            }

            if (!userEmail.equals(principal.getName())) {
                updateMap.put("success", false);
                updateMap.put("message", "수정 권한이 없습니다");
                return new ResponseEntity<>(updateMap, HttpStatus.FORBIDDEN);
            }

            freeBoardComment.setContent(freeBoardCommentDTO.getContent());
            freeBoardCommentService.update(freeBoardComment);
            updateMap.put("success", true);
            return new ResponseEntity<>(updateMap, HttpStatus.OK);

        } catch (Exception e) {
            updateMap.put("success", false);
            updateMap.put("message", "댓글 수정 중 예기치 않은 오류가 발생했습니다");
            return new ResponseEntity<>(updateMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

