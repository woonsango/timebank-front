package com.capstone.timepay.service.board.service;

import com.capstone.timepay.domain.board.Board;
import com.capstone.timepay.domain.board.BoardRepository;
import com.capstone.timepay.domain.comment.Comment;
import com.capstone.timepay.domain.comment.CommentRepository;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import com.capstone.timepay.service.board.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Page<CommentDTO> getAllComments(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<Comment> comments = commentRepository.findAll(pageable);
        List<CommentDTO> commentDTOS = new ArrayList<>();

        for (Comment comment : comments) {
            CommentDTO commentDTO = new CommentDTO();

            FreeBoardComment freeBoardComment = comment.getFreeBoardComment();
            if (freeBoardComment != null) {
                FreeBoardCommentDTO freeBoardCommentDTO = FreeBoardCommentDTO.toFreeBoardCommentDTO(freeBoardComment);
                commentDTO.setFreeBoardCommentDTO(freeBoardCommentDTO);
            }

            DealBoardComment dealBoardComment = comment.getDealBoardComment();
            if (dealBoardComment != null) {
                DealBoardCommentDTO dealBoardCommentDTO = DealBoardCommentDTO.toDealBoardCommentDTO(dealBoardComment);
                commentDTO.setDealBoardCommentDTO(dealBoardCommentDTO);
            }

            commentDTOS.add(commentDTO);
        }

        return new PageImpl<>(commentDTOS, pageable, comments.getTotalElements());
    }
}
