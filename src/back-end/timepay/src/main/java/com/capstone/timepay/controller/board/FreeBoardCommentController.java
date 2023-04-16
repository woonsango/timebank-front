package com.capstone.timepay.controller.board;

import com.capstone.timepay.controller.board.annotation.Response;
import com.capstone.timepay.controller.board.request.ReportRequestDTO;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/free-boards/comments")
public class FreeBoardCommentController {

    private final FreeBoardCommentService freeBoardCommentService;
    private final ReportService reportService;

    // 댓글 작성
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{boardId}")
    public Response writeComment(@PathVariable("boardId") Long boardId, @RequestBody FreeBoardCommentDTO freeBoardCommentDTO)
    {
        // TODO: 추후 User 정보는 세션을 통해 주고받도록 수정
        return new Response("SUCCESS", "댓글 작성", freeBoardCommentService.writeComment(boardId, freeBoardCommentDTO, freeBoardCommentDTO.getUid()));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{boardId}")
    public Response getComments(@PathVariable("boardId") Long boardId)
    {
        return new Response("성공", "댓글을 불러왔습니다.", freeBoardCommentService.getComments(boardId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{boardId}/{commentId}")
    public Response deleteComment(@PathVariable("boardId") Long boardId, @PathVariable("commentId") Long commentId)
    {
        // TODO: 후 JWT 로그인 기능을 추가하고나서, 세션에 로그인된 유저와 댓글 작성자를 비교해서,
        //  맞으면 삭
        return new Response("SUCCESS", "댓글 삭제", freeBoardCommentService.deleteComment(commentId));
    }

    @PostMapping("/{boardId}/{commentId}/report")
    @ApiOperation(value = "신고 API", notes = "JWT 토큰으로 유저를 구분하여 신고 DB에 작성합니다.")
    public ResponseEntity<?> report(@PathVariable("boardId") Long boardId, @PathVariable("commentId") Long commentId, @RequestBody ReportRequestDTO requestDTO) {
        /* 현재 인증된 사용자의 인증 토큰을 가져온다.*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean report = reportService.reportComment(authentication, boardId, commentId, requestDTO, "일반댓글신고");

        if(report)
            return ResponseEntity.ok(report);

        else {
            return ResponseEntity.ok(report + "\n잘못된 유형의 신고 또는 없는 유저입니다.");
        }
    }
}
