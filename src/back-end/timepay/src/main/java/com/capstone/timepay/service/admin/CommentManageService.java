package com.capstone.timepay.service.admin;

import com.capstone.timepay.controller.admin.response.comment.CommentResponse;
import com.capstone.timepay.domain.comment.Comment;
import com.capstone.timepay.domain.comment.CommentRepository;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardCommentRepository;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReportRepository;
import com.capstone.timepay.domain.freeBoard.FreeBoardRepository;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentManageService {

    private final CommentRepository commentRepository;
    private final FreeBoardCommentRepository freeBoardCommentRepository;
    private final FreeBoardRepository freeBoardRepository;
    private final DealBoardReportRepository dealBoardReportRepository;
    private final DealBoardCommentRepository dealBoardCommentRepository;
    public List<CommentResponse> showAllComments() {

        List<Comment> freeComments = commentRepository.findAll().stream().filter(comment -> comment.getDCommentId() == 0).collect(Collectors.toUnmodifiableList());
        List<Comment> dealComments = commentRepository.findAll().stream().filter(comment -> comment.getFCommentId() == 0).collect(Collectors.toUnmodifiableList());

        List<CommentResponse> responses = new ArrayList<>();
        for(Comment comment : freeComments){
            FreeBoardComment fComment = freeBoardCommentRepository.findById(comment.getFCommentId()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 댓글입니다."));
            CommentResponse element = CommentResponse.builder()
                                                        .commentId(comment.getCommentId())
                                                        .originBoardId(comment.getFBoardId())
                                                        .originCommentId(comment.getFCommentId())
                                                        .applyYN(false)
                                                        .selectYN(false)
                                                        .content(fComment.getContent())
                                                        .writerId(fComment.getUser().getUserId())
                                                        .writerName(fComment.getUser().getName())
                                                        .writtenTime(fComment.getCreatedAt())
                                                        .originWriterYN(false)
                                                    .build();
            responses.add(element);
        }
        for(Comment comment : dealComments){
            DealBoardComment dComment = dealBoardCommentRepository.findById(comment.getDCommentId()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 댓글입니다."));
            CommentResponse element = CommentResponse.builder()
                    .commentId(comment.getCommentId())
                    .originBoardId(comment.getDBoardId())
                    .originCommentId(comment.getDCommentId())
                    .applyYN(dComment.isApplied())
                    .selectYN(dComment.isAdopted())
                    .content(dComment.getContent())
                    .writerId(dComment.getUser().getUserId())
                    .writerName(dComment.getUser().getName())
                    .writtenTime(dComment.getCreatedAt())
                    .originWriterYN(false)
                    .build();
            responses.add(element);
        }

        return responses.stream().sorted(Comparator.comparing(CommentResponse::getCommentId)).collect(Collectors.toUnmodifiableList());

    }

    public void deleteComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 댓글입니다."));

        // 지워야 하는 댓글이 거래 게시판 댓글일 경우 => 해당 댓글 지움
        if(comment.getFCommentId() == 0) dealBoardCommentRepository.deleteById(comment.getDCommentId());
        else freeBoardCommentRepository.deleteById(comment.getFCommentId());

        // 댓글 전체 테이블의 컬럼도 지움
        commentRepository.deleteById(commentId);

    }
}
