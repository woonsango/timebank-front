package com.capstone.timepay.controller.board;

import com.capstone.timepay.controller.board.annotation.Response;
import com.capstone.timepay.controller.board.request.ReportRequestDTO;
import com.capstone.timepay.service.board.dto.DealBoardCommentDTO;
import com.capstone.timepay.service.board.service.DealBoardCommentService;
import com.capstone.timepay.service.board.service.ReportService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.UnexpectedTypeException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deal-boards")
public class DealBoardCommentController {

    private final DealBoardCommentService dealBoardCommentService;
    private final ReportService reportService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/comments/{boardId}")
    public Response writeComment(@PathVariable("boardId") Long boardId, @RequestBody DealBoardCommentDTO dealBoardCommentDTO)
    {
        return new Response("SUCCESS", "댓글 작성", dealBoardCommentService.writeComment(boardId, dealBoardCommentDTO, dealBoardCommentDTO.getUid()));
    }

    // 게시글에 달린 댓글 모두 불러오기
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/comments/{boardId}")
    public Response getComments(@PathVariable("boardId") Long boardId) {
        return new Response("성공", "댓글 불러오기.", dealBoardCommentService.getComments(boardId));
    }

    // 댓글 삭제
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/comments/{boardId}/{commentId}")
    public Response deleteComment(@PathVariable("boardId") Long boardId, @PathVariable("commentId") Long commentId) {
        // TODO: 추후 JWT 로그인 기능을 추가하고나서, 세션에 로그인된 유저와 댓글 작성자를 비교해서, 맞으면 삭제 진행하고
        // 틀리다면 예외처리를 해주면 된다.

        return new Response("성공", "댓글 삭제", dealBoardCommentService.deleteComment(commentId));
    }

    @PostMapping("/comments/{boardId}/{commentId}/report")
    @ApiOperation(value = "신고 API", notes = "JWT 토큰으로 유저를 구분하여 신고 DB에 작성합니다.")
    public ResponseEntity<?> report(@PathVariable("boardId") Long boardId, @PathVariable("commentId") Long commentId, @RequestBody ReportRequestDTO requestDTO) {
        /* 현재 인증된 사용자의 인증 토큰을 가져온다.*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean report = reportService.reportComment(authentication, boardId, commentId, requestDTO, "거래댓글신고");

        if(report)
            return ResponseEntity.ok(report);
        else {
            return ResponseEntity.ok(report + "\n잘못된 유형의 신고 또는 없는 유저입니다.");
        }
    }
}
