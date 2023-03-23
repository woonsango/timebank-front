package com.capstone.timepay.service.board.service;

import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardRepository;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardCommentRepository;
import com.capstone.timepay.domain.user.TestUser;
import com.capstone.timepay.service.board.dto.DealBoardCommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DealBoardCommentService {

    private final DealBoardCommentRepository dealBoardCommentRepository;
    private final DealBoardRepository dealBoardRepository;

    // 댓글 작성하기
    @Transactional
    public DealBoardCommentDTO writeComment(Long boardId, DealBoardCommentDTO dealBoardCommentDTO, TestUser testUser)
    {
        DealBoardComment dealBoardComment = new DealBoardComment();
        dealBoardComment.setContent(dealBoardCommentDTO.getContent());

        DealBoard dealBoard = dealBoardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("게시판을 찾을 수 없음");
        });

        dealBoardComment.setTestUser(testUser);
        dealBoardComment.setDealBoard(dealBoard);
        dealBoardComment.setApplied(false);
        dealBoardComment.setAdopted(false);
        // TODO: 유저에 따라 숨김처리 하냐 안하냐 결정

        dealBoardCommentRepository.save(dealBoardComment);

        return DealBoardCommentDTO.toDealBoardCommentDTO(dealBoardComment);
    }

    // 전체 댓글 불러오기
    // TODO: 비공개 유저일때 제거해야함
    @Transactional(readOnly = true)
    public List<DealBoardCommentDTO> getComments(Long boardId)
    {
        List<DealBoardComment> comments = dealBoardCommentRepository.findAllByDealBoardCommentId(boardId);
        List<DealBoardCommentDTO> commentDTOS = new ArrayList<>();

        comments.forEach(s -> commentDTOS.add(DealBoardCommentDTO.toDealBoardCommentDTO(s)));
        return commentDTOS;
    }

    // 삭제
    @Transactional
    public String deleteComment(Long commentId)
    {
        DealBoardComment dealBoardComment = dealBoardCommentRepository.findById(commentId).orElseThrow(() -> {
            return new IllegalArgumentException("댓글 Id를 찾을 수 없습니다.");
        });
        dealBoardCommentRepository.deleteById(commentId);
        return "삭제 완료";
    }



}
