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
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.service.admin.dto.CommentHideDto;
import com.capstone.timepay.service.admin.dto.CommentSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
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
    private final UserRepository userRepository;

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
            Comment comment = commentRepository.findByCommentId(commentId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 댓글입니다."));

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

    public Page<CommentResponse> showCommentsBySearch(CommentSearchDto commentSearchDto) {

        if(!ObjectUtils.isEmpty(commentSearchDto.getCommentId()) &&
            ObjectUtils.isEmpty(commentSearchDto.getName()) &&
            ObjectUtils.isEmpty(commentSearchDto.getContent()) &&
            ObjectUtils.isEmpty(commentSearchDto.getStartTime()) &&
            ObjectUtils.isEmpty(commentSearchDto.getEndTime())){

            Comment comment = commentRepository.findByCommentId(commentSearchDto.getCommentId()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 댓글입니다."));
            List<CommentResponse> response = new ArrayList<>();
            if(!Objects.isNull(comment.getFreeBoardComment())) {
                List<FreeBoardComment> temp = new ArrayList<>();
                temp.add(comment.getFreeBoardComment());
                response = convertFCommentsToResponse(temp);
            }
            else{
                List<DealBoardComment> temp = new ArrayList<>();
                temp.add(comment.getDealBoardComment());
                response = convertDCommentsToResponse(temp);
            }
            return new PageImpl<>(response);
        }
        else if(ObjectUtils.isEmpty(commentSearchDto.getCommentId()) &&
                !ObjectUtils.isEmpty(commentSearchDto.getName()) &&
                ObjectUtils.isEmpty(commentSearchDto.getContent()) &&
                ObjectUtils.isEmpty(commentSearchDto.getStartTime()) &&
                ObjectUtils.isEmpty(commentSearchDto.getEndTime())){

            User user = userRepository.findByName(commentSearchDto.getName());
            List<CommentResponse> fComments = convertFCommentsToResponse(freeBoardCommentRepository.findAllByUser(user));
            List<CommentResponse> dComments = convertDCommentsToResponse(dealBoardCommentRepository.findAllByUser(user));

            List<CommentResponse> commentResponses = new ArrayList<>();
            commentResponses.addAll(fComments);
            commentResponses.addAll(dComments);

            return new PageImpl<>(commentResponses);
        }
        else if(ObjectUtils.isEmpty(commentSearchDto.getCommentId()) &&
                ObjectUtils.isEmpty(commentSearchDto.getName()) &&
                !ObjectUtils.isEmpty(commentSearchDto.getContent()) &&
                ObjectUtils.isEmpty(commentSearchDto.getStartTime()) &&
                ObjectUtils.isEmpty(commentSearchDto.getEndTime())){

            List<CommentResponse> fComments = convertFCommentsToResponse(freeBoardCommentRepository.findByContentContains(commentSearchDto.getContent()));
            List<CommentResponse> dComments = convertDCommentsToResponse(dealBoardCommentRepository.findByContentContains(commentSearchDto.getContent()));

            List<CommentResponse> commentResponses = new ArrayList<>();
            commentResponses.addAll(fComments);
            commentResponses.addAll(dComments);

            return new PageImpl<>(commentResponses);
        }
        else if(ObjectUtils.isEmpty(commentSearchDto.getCommentId()) &&
                ObjectUtils.isEmpty(commentSearchDto.getName()) &&
                ObjectUtils.isEmpty(commentSearchDto.getContent()) &&
                !ObjectUtils.isEmpty(commentSearchDto.getStartTime()) &&
                !ObjectUtils.isEmpty(commentSearchDto.getEndTime())){

            List<CommentResponse> fComments =
                    convertFCommentsToResponse(freeBoardCommentRepository.findByCreatedAtLessThanEqualAndCreatedAtGreaterThanEqual(commentSearchDto.getEndTime(),commentSearchDto.getStartTime()));
            List<CommentResponse> dComments =
                    convertDCommentsToResponse(dealBoardCommentRepository.findByCreatedAtLessThanEqualAndCreatedAtGreaterThanEqual(commentSearchDto.getEndTime(),commentSearchDto.getStartTime()));

            List<CommentResponse> commentResponses = new ArrayList<>();
            commentResponses.addAll(fComments);
            commentResponses.addAll(dComments);

            return new PageImpl<>(commentResponses);
        }
        else throw new IllegalArgumentException("잘못된 파라미터 요청입니다.");

    }
    public List<CommentResponse> convertFCommentsToResponse(List<FreeBoardComment> fComments){
        return fComments
                .stream()
                .map(freeBoardComment ->
                        CommentResponse.builder()
                                .commentId(commentRepository.findByFreeBoardComment(freeBoardComment).getCommentId())
                                .originBoardId(freeBoardComment.getFreeBoard().getF_boardId())
                                .originCommentId(freeBoardComment.getF_commentId())
                                .applyYN(false)
                                .selectYN(false)
                                .content(freeBoardComment.getContent())
                                .writerId(freeBoardComment.getUser().getUserId())
                                .writerName(freeBoardComment.getUser().getName())
                                .writtenTime(freeBoardComment.getCreatedAt())
                                .originWriterYN(false)
                                .build())
                .collect(Collectors.toList());
    }
    public List<CommentResponse> convertDCommentsToResponse(List<DealBoardComment> dComments){
        return dComments
                .stream()
                .map(dealBoardComment ->
                        CommentResponse.builder()
                                .commentId(commentRepository.findByDealBoardComment(dealBoardComment).getCommentId())
                                .originBoardId(dealBoardComment.getDealBoard().getD_boardId())
                                .originCommentId(dealBoardComment.getD_commentId())
                                .applyYN(dealBoardComment.isApplied())
                                .selectYN(dealBoardComment.isAdopted())
                                .content(dealBoardComment.getContent())
                                .writerId(dealBoardComment.getUser().getUserId())
                                .writerName(dealBoardComment.getUser().getName())
                                .writtenTime(dealBoardComment.getCreatedAt())
                                .originWriterYN(false)
                                .build())
                .collect(Collectors.toList());
    }
}
