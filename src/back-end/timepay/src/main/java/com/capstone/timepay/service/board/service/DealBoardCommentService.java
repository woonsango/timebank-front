package com.capstone.timepay.service.board.service;

import com.capstone.timepay.domain.comment.Comment;
import com.capstone.timepay.domain.comment.CommentRepository;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardRepository;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardCommentRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
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
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    // 댓글 작성하기
    @Transactional
    public DealBoardCommentDTO writeComment(Long boardId, DealBoardCommentDTO dealBoardCommentDTO,
                                            String email)
    {
        User user = userRepository.findByEmail(email).orElse(null);
        DealBoard dealBoard = dealBoardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("게시판을 찾을 수 없음");
        });

        DealBoardComment dealBoardComment = DealBoardComment.builder()
                .content(dealBoardCommentDTO.getContent())
                .isHidden(dealBoardCommentDTO.isHidden())
                .isApplied(false)
                .isApplied(false)
                .user(user)
                .dealBoard(dealBoard)
                .build();

        Comment comment = Comment.builder().
                freeBoardComment(null).
                dealBoardComment(dealBoardComment).
                build();
        commentRepository.save(comment);

        dealBoardCommentRepository.save(dealBoardComment);

        return DealBoardCommentDTO.toDealBoardCommentDTO(dealBoardComment);
    }

    // 전체 댓글 불러오기
    // TODO: 비공개 유저일때 제거해야함
    @Transactional(readOnly = true)
    public List<DealBoardCommentDTO> getComments(Long boardId)
    {
        List<DealBoardComment> comments = dealBoardCommentRepository.findAllByDealBoard(dealBoardRepository.findById(boardId).get());
        List<DealBoardCommentDTO> commentDTOS = new ArrayList<>();

        comments.forEach(s -> commentDTOS.add(DealBoardCommentDTO.toDealBoardCommentDTO(s)));
        return commentDTOS;
    }

    // 삭제
    @Transactional(readOnly = true)
    public void delete(Long commentId) {
        DealBoardComment dealBoardComment = dealBoardCommentRepository.findById(commentId).orElseThrow(() -> {
            return new IllegalArgumentException("Comment Id를 찾을 수 없습니다");
        });
        dealBoardCommentRepository.deleteById(commentId);
    }

    @Transactional(readOnly = true)
    public DealBoardComment getCommentId(Long id)
    {
        return dealBoardCommentRepository.findById(id).orElse(null);
    }

    public String getEmail(Long commentId) {
        DealBoardComment dealBoardComment = dealBoardCommentRepository.findById(commentId).orElse(null);
        User user = dealBoardComment.getUser();
        return user.getEmail();
    }
}
