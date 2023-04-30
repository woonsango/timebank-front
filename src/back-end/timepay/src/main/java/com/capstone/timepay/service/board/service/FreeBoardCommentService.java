package com.capstone.timepay.service.board.service;

import com.capstone.timepay.domain.board.Board;
import com.capstone.timepay.domain.comment.Comment;
import com.capstone.timepay.domain.comment.CommentRepository;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.freeBoard.FreeBoardRepository;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardCommentRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.service.board.dto.FreeBoardCommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FreeBoardCommentService {
    private final FreeBoardCommentRepository freeBoardCommentRepository;
    private final FreeBoardRepository freeBoardRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final FreeBoardService freeBoardService;
    // 댓글 작성
    @Transactional
    public FreeBoardCommentDTO writeComment(Long boardId, FreeBoardCommentDTO freeBoardCommentDTO, String email)
    {
        User user = userRepository.findByEmail(email).orElse(null);
        FreeBoard freeBoard = freeBoardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("게시판을 찾을 수 없습니다.");
        });

        FreeBoardComment freeBoardComment = FreeBoardComment.builder()
                .content(freeBoardCommentDTO.getContent())
                .isHidden(freeBoardCommentDTO.isHidden())
                .freeBoard(freeBoard)
                .user(user)
                .build();
        freeBoardCommentRepository.save(freeBoardComment);

        Comment comment = Comment.builder().
                freeBoardComment(freeBoardComment).
                dealBoardComment(null).
                build();
        commentRepository.save(comment);

        return FreeBoardCommentDTO.toFreeBoardCommentDTO(freeBoardComment);
    }

    // 현재 글의 전체 댓글 불러오기
    @Transactional(readOnly = true)
    public List<FreeBoardCommentDTO> getComments(Long boardId)
    {
        List<FreeBoardComment> comments = freeBoardCommentRepository.findAllByFreeBoard(freeBoardRepository.findById(boardId).get());
        List<FreeBoardCommentDTO> commentDTOS = new ArrayList<>();

        comments.forEach(s -> commentDTOS.add(FreeBoardCommentDTO.toFreeBoardCommentDTO(s)));
        return commentDTOS;
    }

    @Transactional(readOnly = true)
    public FreeBoardComment getCommentId(Long id)
    {
        return freeBoardCommentRepository.findById(id).orElse(null);
    }

    // 댓글 삭제
    @Transactional
    public String delete(Long commentId)
    {
        FreeBoardComment freeBoardComment = freeBoardCommentRepository.findById(commentId).orElseThrow(() -> {
            return new IllegalArgumentException("댓글을 찾을 수 없습니다");
        });
        freeBoardCommentRepository.deleteById(commentId);
        return "삭제 완료";
    }

    // 댓글 수정
    @Transactional
    public void update(FreeBoardComment freeBoardComment) {
        freeBoardCommentRepository.save(freeBoardComment);
    }

    public String getEmail(Long id)
    {
        FreeBoardComment freeBoardComment = freeBoardCommentRepository.findById(id).orElse(null);
        User user = freeBoardComment.getUser();
        return user.getEmail();
    }
}
