package com.capstone.timepay.controller.board;


import com.capstone.timepay.controller.board.request.ReportRequestDTO;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardRepository;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.service.board.dto.DealBoardCommentDTO;
import com.capstone.timepay.service.board.service.DealBoardCommentService;
import com.capstone.timepay.service.board.service.DealRegisterService;
import com.capstone.timepay.service.board.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deal-boards/comments")
public class DealBoardCommentController {

    private final DealBoardCommentService dealBoardCommentService;
    private final DealBoardRepository dealBoardRepository;
    private final DealBoardController dealBoardController;
    private final DealRegisterService dealRegisterService;

    private final ReportService reportService;

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
        DealBoard dealBoard = dealBoardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("게시판을 찾을 수 없음");
        });
        if(dealBoardCommentDTO.isApplied()) // 지원 댓글이고
        {
            if(dealBoard.isAuto()) // 자동 매칭 True이면
            {
                dealBoardController.readyToStart(boardId, principal);

                DealBoardCommentDTO response = dealBoardCommentService.writeComment(boardId, dealBoardCommentDTO, principal.getName());
                dealBoardCommentService.setAdoptedComments(response.getId());
                return new ResponseEntity(response, HttpStatus.OK);

            }
        }
        return new ResponseEntity(dealBoardCommentService.writeComment(boardId, dealBoardCommentDTO, principal.getName()), HttpStatus.OK);
    }

    // 댓글 삭제
    @ApiOperation(value = "거래게시글 댓글 삭제")
    @DeleteMapping("delete/{boardId}/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("boardId") Long boardId,
                                                @PathVariable("commentId") Long commentId,
                                                Principal principal) {
        String userEmail = dealRegisterService.getEmail(boardId);
        if (!principal.getName().equals(userEmail)) {
            throw new IllegalArgumentException("해당 댓글을 지울 권한이 없습니다");
        }

        boolean isDeleted = dealBoardCommentService.deleteCommentById(commentId);
        if (isDeleted) {
            return new ResponseEntity<>("댓글이 삭제되었습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("댓글 삭제에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{boardId}/{commentId}/report")
    @ApiOperation(value = "신고 API", notes = "JWT 토큰으로 유저를 구분하여 신고 DB에 작성합니다.")
    public ResponseEntity<?> report(@PathVariable("boardId") Long boardId, @PathVariable("commentId") Long commentId, @RequestBody ReportRequestDTO requestDTO) {
        /* 현재 인증된 사용자의 인증 토큰을 가져온다.*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(reportService.reportComment(authentication, boardId, commentId, requestDTO, "거래댓글신고"));
    }

    @ApiOperation(value = "현재 게시판에서 지원한 사람들의 목록 가져오기")
    @GetMapping("/{boardId}/applied")
    public List<DealBoardCommentDTO> getAppliedComments(@PathVariable("boardId") Long boardId) {
        return dealBoardCommentService.getAppliedComments(boardId);
    }

    @ApiOperation(value = "지원한 목록 중 댓글 선정하기")
    @PutMapping("/{boardId}/adopted/{commentId}")
    public DealBoardCommentDTO getAdoptedComments(@PathVariable("boardId") Long boardId,
                                                  @PathVariable("commentId") Long commentId)
    {
        return dealBoardCommentService.setAdoptedComments(commentId);
    }

    @ApiOperation(value = "선정된 댓글목록 확인")
    @GetMapping("/{boardId}/adopted")
    public List<DealBoardCommentDTO> getAdoptedComments(@PathVariable("boardId") Long boardId) {
        return dealBoardCommentService.getAdoptedComments(boardId);
    }
}

