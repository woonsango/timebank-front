package com.capstone.timepay.service.admin;

import com.capstone.timepay.controller.admin.response.comment.CommentResponse;
import com.capstone.timepay.controller.admin.response.inquiry.InquiryResponse;
import com.capstone.timepay.domain.comment.Comment;
import com.capstone.timepay.domain.comment.CommentRepository;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardCommentRepository;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReportRepository;
import com.capstone.timepay.domain.freeBoard.FreeBoardRepository;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardCommentRepository;
import com.capstone.timepay.domain.inquiry.Inquiry;
import com.capstone.timepay.service.admin.dto.CommentHideDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
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

    public Page<CommentResponse> convertResponsePages(Page<Comment> pages){
        Page<CommentResponse> pageResponses = pages.map(new Function<Comment, CommentResponse>() {
            @Override
            public CommentResponse apply(Comment comment) {

                // 1. 자유 게시판 댓글
                if(Objects.isNull(comment.getDealBoardComment())){
                    return CommentResponse.builder()
                            .commentId(comment.getCommentId())
                            .originBoardId(comment.getFreeBoardComment().getFreeBoard().getF_boardId())
                            .originCommentId(comment.getFreeBoardComment().getF_commentId())
                            .applyYN(false)
                            .selectYN(false)
                            .content(comment.getFreeBoardComment().getContent())
                            .writerId(comment.getFreeBoardComment().getUser().getUserId())
                            .writerName(comment.getFreeBoardComment().getUser().getName())
                            .writtenTime(comment.getFreeBoardComment().getCreatedAt())
                            .originWriterYN(false)
                            .build();
                }
                else{
                    // 2. 거래 게시판 댓글
                    return CommentResponse.builder()
                            .commentId(comment.getCommentId())
                            .originBoardId(comment.getDealBoardComment().getDealBoard().getD_boardId())
                            .originCommentId(comment.getDealBoardComment().getD_commentId())
                            .applyYN(comment.getDealBoardComment().isApplied())
                            .selectYN(comment.getDealBoardComment().isAdopted())
                            .content(comment.getDealBoardComment().getContent())
                            .writerId(comment.getDealBoardComment().getUser().getUserId())
                            .writerName(comment.getDealBoardComment().getUser().getName())
                            .writtenTime(comment.getDealBoardComment().getCreatedAt())
                            .originWriterYN(false)
                            .build();
                }

            }
        });

        return pageResponses;
    }

    public Page<CommentResponse> showAllComments(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<Comment> comments = commentRepository.findAll(pageable);

        return convertResponsePages(comments);
    }

    public void hideComment(CommentHideDto comments) {

        for(Long commentId : comments.getCommentIds()){
            Comment comment = commentRepository.findById(commentId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 댓글입니다."));

            // 1. 자유 게시판 댓글인 경우
            if(Objects.isNull(comment.getDealBoardComment())){
                comment.getFreeBoardComment().updateIsHidden(true);
                freeBoardCommentRepository.save(comment.getFreeBoardComment());
            }
            else{   // 2. 거래 게시판 댓글인 경우
                comment.getDealBoardComment().updateIsHidden(true);
                dealBoardCommentRepository.save(comment.getDealBoardComment());
            }
        }

    }
}
