package com.capstone.timepay.controller.board;

import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import com.capstone.timepay.service.board.dto.DealBoardCommentDTO;
import com.capstone.timepay.service.board.dto.DealBoardDTO;
import com.capstone.timepay.service.board.dto.FreeBoardCommentDTO;
import com.capstone.timepay.service.board.service.DealBoardCommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deal-boards/comments")
public class DealBoardCommentController {

    private final DealBoardCommentService dealBoardCommentService;

    @ApiOperation(value = "거래게시글 게시판의 모든 댓글 불러오기")
    // 게시글에 달린 댓글 모두 불러오기
    @GetMapping("/{boardId}")
    public ResponseEntity getDealBoardComments(@PathVariable("boardId") Long boardId) {
        return new ResponseEntity(dealBoardCommentService.getComments(boardId), HttpStatus.OK);
    }

    @ApiOperation(value = "거래게시글 댓글작성")
    @PostMapping("/write/{boardId}")
    public ResponseEntity writeDealBoardComment(@PathVariable("boardId") Long boardId,
                                                @RequestBody DealBoardCommentDTO dealBoardCommentDTO,
                                                Principal principal)
    {
        return new ResponseEntity(dealBoardCommentService.writeComment(boardId, dealBoardCommentDTO, principal.getName()), HttpStatus.OK);
    }

    // 댓글 삭제
    @ApiOperation(value = "거래게시글 댓글 삭제")
    @DeleteMapping("/comments/{boardId}/{commentId}")
    public Map<String, Object> deleteDealBoardComment(@RequestBody DealBoardCommentDTO dealBoardCommentDTO,
                                                      @PathVariable("boardId") Long boardId,
                                                      @PathVariable("commentId") Long commentId,
                                                      Principal principal) {
        Map<String, Object> deleteMap = new HashMap<>();
        DealBoardComment dealBoardComment = dealBoardCommentService.getCommentId(commentId);
        String userEmail = dealBoardCommentService.getEmail(commentId);

        if (dealBoardComment == null)
        {
            deleteMap.put("success", false);
            deleteMap.put("message", "해당 댓글을 찾을 수가 없습니다");
        }

        if (!userEmail.equals(principal.getName()))
        {
            deleteMap.put("success", false);
            deleteMap.put("message", "삭제 권한이 없습니다");
        }
        dealBoardCommentService.delete(commentId);
        deleteMap.put("success", true);
        return deleteMap;
    }

}