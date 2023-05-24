package com.capstone.timepay.service.board.service;

import com.capstone.timepay.domain.board.BoardStatus;
import com.capstone.timepay.domain.comment.Comment;
import com.capstone.timepay.domain.comment.CommentRepository;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardRepository;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardCommentRepository;
import com.capstone.timepay.domain.organization.Organization;
import com.capstone.timepay.domain.organization.OrganizationRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.service.board.dto.AdoptedCommentDTO;
import com.capstone.timepay.service.board.dto.DealBoardCommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DealBoardCommentService {

    private final DealBoardCommentRepository dealBoardCommentRepository;
    private final DealBoardRepository dealBoardRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final DealBoardService dealBoardService;
    private final OrganizationRepository organizationRepository;
    // 댓글 작성하기
    @Transactional
    public DealBoardCommentDTO writeComment(Long boardId, DealBoardCommentDTO dealBoardCommentDTO,
                                            Principal principal)
    {
        User user = userRepository.findByEmail(principal.getName()).orElse(null);

        if (user == null)
        {
            Organization organization = organizationRepository.findByAccount(principal.getName()).orElse(null);
            if (organization == null)
                throw new IllegalArgumentException("유저가 존재하지 않습니다");
            user = userRepository.findByOrganization(organization).orElseThrow(() -> {
                return new IllegalArgumentException("그냥 유저가 존재하지 않습니다");
            });
        }

        DealBoard dealBoard = dealBoardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("게시판을 찾을 수 없음");
        });

        DealBoardComment dealBoardComment = DealBoardComment.builder()
                .content(dealBoardCommentDTO.getContent())
                .isHidden(dealBoardCommentDTO.isHidden())
                .isAdopted(false)
                .isApplied(dealBoardCommentDTO.isApplied())
                .user(user)
                .dealBoard(dealBoard)
                .build();
        dealBoardService.insertComment(boardId, dealBoardComment);

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

    public List<DealBoardCommentDTO> getAppliedComments(Long boardId) {
        DealBoard dealBoard = dealBoardRepository.findById(boardId).get();
        List<DealBoardComment> comments = dealBoardCommentRepository.findAllByDealBoardAndIsAppliedTrue(dealBoard);
        return DealBoardCommentDTO.toDealBoardCommentDTOs(comments);
    }

    public DealBoardCommentDTO setAdoptedComments(Long commentId)
    {
        DealBoardComment dealBoardComment = dealBoardCommentRepository.findById(commentId).orElseThrow(() -> {
            return new IllegalArgumentException("댓글을 찾을 수 없음");
        });
        if (dealBoardComment.isAdopted() == false)
        {
            dealBoardComment.setAdopted(true);
            dealBoardCommentRepository.save(dealBoardComment);
        }
        return DealBoardCommentDTO.toDealBoardCommentDTO(dealBoardComment);
    }

    public List<DealBoardCommentDTO> getAdoptedComments(Long boardId)
    {
        DealBoard dealBoard = dealBoardRepository.findById(boardId).get();
        List<DealBoardComment> comments = dealBoardCommentRepository.findAllByDealBoardAndIsAdoptedTrue(dealBoard);
        return DealBoardCommentDTO.toDealBoardCommentDTOs(comments);
    }

    @Transactional
    public boolean deleteCommentById(Long commentId) {
        DealBoardComment dealBoardComment = dealBoardCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        // Comment 엔티티 삭제
        Comment comment = commentRepository.findByDealBoardComment(dealBoardComment);
        if (comment != null) {
            commentRepository.delete(comment);
        }

        // DealBoardComment 엔티티 삭제
        dealBoardCommentRepository.delete(dealBoardComment);
        return true;
    }

    public ResponseEntity setAdoptedAllComments(Long boardId, AdoptedCommentDTO commentIds)
    {
        List<Long> commentId = commentIds.getCommentsId();
        for (int i = 0; i < commentId.size(); i++)
        {
            DealBoardComment dealBoardComment = dealBoardCommentRepository.findById(commentId.get(i)).orElseThrow(() -> {
                return new IllegalArgumentException("댓글을 찾을 수 없음");
            });
            dealBoardComment.setAdopted(true);
            dealBoardCommentRepository.save(dealBoardComment);
        }
        DealBoard dealBoard = dealBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        dealBoard.setBoardStatus(BoardStatus.MATCHING_COMPLETE);
        dealBoardRepository.save(dealBoard);
        return new ResponseEntity("댓글 지원 완료, 매칭 완료", HttpStatus.OK);
    }

    public ResponseEntity updateComment(Long commentId, DealBoardCommentDTO dealBoardCommentDTO)
    {
        DealBoardComment dealBoardComment = dealBoardCommentRepository.findById(commentId).orElseThrow(() -> {
            return new IllegalArgumentException("댓글을 찾을 수 없음");
        });

        dealBoardComment.setAdopted(dealBoardCommentDTO.isAdopted());
        dealBoardComment.setUpdatedAt(LocalDateTime.now());
        dealBoardComment.setContent(dealBoardCommentDTO.getContent());
        dealBoardComment.setHidden(dealBoardCommentDTO.isHidden());
        dealBoardCommentRepository.save(dealBoardComment);
        return new ResponseEntity(HttpStatus.OK);
    }
}
