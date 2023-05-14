package com.capstone.timepay.service.admin;

import com.capstone.timepay.controller.admin.response.comment.CommentResponse;
import com.capstone.timepay.domain.board.Board;
import com.capstone.timepay.domain.board.BoardRepository;
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

    private final DealBoardCommentRepository dealBoardCommentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

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
                            .writerNickname(comment.getFreeBoardComment().getUser().getNickname())
                            .isHidden(comment.getFreeBoardComment().isHidden())
                            .updatedTime(comment.getFreeBoardComment().getUpdatedAt())
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
                            .writerNickname(comment.getDealBoardComment().getUser().getNickname())
                            .isHidden(comment.getDealBoardComment().isHidden())
                            .updatedTime(comment.getDealBoardComment().getUpdatedAt())
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

    public Page<CommentResponse> showCommentsBySearch(String query, String value) {

        if(ObjectUtils.isEmpty(query) || ObjectUtils.isEmpty(value)) throw new IllegalArgumentException("잘못된 파라미터 요청입니다.");

        if(query.equals("boardId")){
            Board board = boardRepository.findById(Long.parseLong(value)).orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
            List<CommentResponse> commentResponses = new ArrayList<>();

            if(!Objects.isNull(board.getFreeBoard())){
                List<FreeBoardComment> comments = freeBoardCommentRepository.findAllByFreeBoard(board.getFreeBoard());

                List<CommentResponse> fComments = convertFCommentsToResponse(comments);
                commentResponses.addAll(fComments);
            }
            else if(!Objects.isNull(board.getDealBoard())){
                List<DealBoardComment> comments = dealBoardCommentRepository.findAllByDealBoard(board.getDealBoard());

                List<CommentResponse> dComments = convertDCommentsToResponse(comments);
                commentResponses.addAll(dComments);
            }
            else{
                throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
            }
            return new PageImpl<>(commentResponses);
        }
        else if(query.equals("name")){

            List<User> users = userRepository.findAllByNameContains(value);

            if(ObjectUtils.isEmpty(users)) return new PageImpl<>(new ArrayList<>());

            List<CommentResponse> fComments = new ArrayList<>();
            List<CommentResponse> dComments = new ArrayList<>();
            for(User user : users){
                fComments.addAll(convertFCommentsToResponse(freeBoardCommentRepository.findAllByUser(user)));
            }
            for(User user : users){
                dComments.addAll(convertDCommentsToResponse(dealBoardCommentRepository.findAllByUser(user)));
            }

            List<CommentResponse> commentResponses = new ArrayList<>();
            commentResponses.addAll(fComments);
            commentResponses.addAll(dComments);

            return new PageImpl<>(commentResponses);
        }
        else if(query.equals("nickname")){

            List<User> users = userRepository.findAllByNicknameContains(value);
            if(ObjectUtils.isEmpty(users)) return new PageImpl<>(new ArrayList<>());

            List<CommentResponse> fComments = new ArrayList<>();
            List<CommentResponse> dComments = new ArrayList<>();
            for(User user : users){
                fComments.addAll(convertFCommentsToResponse(freeBoardCommentRepository.findAllByUser(user)));
            }
            for(User user : users){
                dComments.addAll(convertDCommentsToResponse(dealBoardCommentRepository.findAllByUser(user)));
            }

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
                                .writerId(freeBoardComment.getUser().getUserId()) //freeBoardComment.getUser().getUserId()
                                .writerName(freeBoardComment.getUser().getName())//freeBoardComment.getUser().getName()
                                .writtenTime(freeBoardComment.getCreatedAt())
                                .originWriterYN(false)
                                .writerNickname(freeBoardComment.getUser().getNickname()) //freeBoardComment.getUser().getNickname()
                                .isHidden(freeBoardComment.isHidden())
                                .updatedTime(freeBoardComment.getUpdatedAt())
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
                                .writerId(dealBoardComment.getUser().getUserId())// dealBoardComment.getUser().getUserId()
                                .writerName(dealBoardComment.getUser().getName()) //dealBoardComment.getUser().getName()
                                .writtenTime(dealBoardComment.getCreatedAt())
                                .originWriterYN(false)
                                .writerNickname(dealBoardComment.getUser().getNickname()) //dealBoardComment.getUser().getNickname()
                                .isHidden(dealBoardComment.isHidden())
                                .updatedTime(dealBoardComment.getUpdatedAt())
                                .build())
                .collect(Collectors.toList());
    }
}
