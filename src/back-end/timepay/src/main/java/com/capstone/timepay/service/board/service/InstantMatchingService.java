package com.capstone.timepay.service.board.service;

import com.capstone.timepay.controller.board.request.InstantMatchingBoardDTO;
import com.capstone.timepay.controller.board.reseponse.InstantMatchingResponseDTO;
import com.capstone.timepay.domain.board.Board;
import com.capstone.timepay.domain.board.BoardRepository;
import com.capstone.timepay.domain.board.BoardStatus;
import com.capstone.timepay.domain.comment.Comment;
import com.capstone.timepay.domain.comment.CommentRepository;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardRepository;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardCommentRepository;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.dealRegister.DealRegisterRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class InstantMatchingService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final DealBoardRepository dealBoardRepository;
    private final DealRegisterRepository dealRegisterRepository;
    private final DealBoardService dealBoardService;
    private final DealBoardCommentRepository dealBoardCommentRepository;
    private final CommentRepository commentRepository;
    private final DealBoardCommentService dealBoardCommentService;
    public InstantMatchingResponseDTO instantMatching(InstantMatchingBoardDTO instantMatchingBoardDTO, String helper) throws IOException {
        User helpUser = userRepository.findByEmail(instantMatchingBoardDTO.getEmail()).orElseThrow(() -> {
            return new IllegalArgumentException("해당 유저를 찾을 수 없습니다.");
        });
        InstantMatchingResponseDTO instantMatchingResponseDTO =
                new InstantMatchingResponseDTO("이게 출력되면 코드에 무언가 말도 안되게 흘러가고 있다는 뜻");
        DealBoard dealBoard = null;
        try {
             dealBoard = DealBoard.builder()
                    .title(instantMatchingBoardDTO.getTitle())
                    .boardStatus(instantMatchingBoardDTO.getState())
                    .content(instantMatchingBoardDTO.getContent())
                    .category(instantMatchingBoardDTO.getCategory())
                    .type("help")
                    .boardStatus(BoardStatus.MATCHING_IN_PROGRESS)
                    .location(instantMatchingBoardDTO.getLocation())
                    .startTime(instantMatchingBoardDTO.getStartTime())
                    .endTime(instantMatchingBoardDTO.getEndTime())
                    .pay(instantMatchingBoardDTO.getPay())
                    .isHidden(instantMatchingBoardDTO.isHidden())
                    .isAuto(instantMatchingBoardDTO.isAuto())
                    .dealBoardComments(new ArrayList<>())
                    .build();
            dealBoardRepository.save(dealBoard);

            Board board = Board.builder().
                    freeBoard(null).
                    dealBoard(dealBoard).
                    build();
            boardRepository.save(board);

            DealRegister dealRegister = DealRegister.builder()
                    .d_registerId(dealBoard.getD_boardId())
                    .dealBoard(dealBoard)
                    .user(helpUser)
                    .build();
            dealRegisterRepository.save(dealRegister);
        } catch (Exception e)
        {
            instantMatchingResponseDTO.setStatus("게시글 처리 도중 에러가 발생했습니다.");
        }
        Long boardId = dealBoard.getD_boardId();
        DealBoardComment dealBoardComment = null;
        /* 댓글 객체 생성 */
        try {
            User helperUser = userRepository.findByEmail(helper).orElseThrow(() -> {
                return new IllegalArgumentException("해당 유저를 찾을 수 없습니다.");
            });

             dealBoardComment = DealBoardComment.builder()
                    .content("즉석 매칭")
                    .isHidden(false)
                    .isAdopted(true)
                    .isApplied(true)
                    .user(helperUser)
                    .dealBoard(dealBoard)
                    .build();
            dealBoardCommentRepository.save(dealBoardComment);
            dealBoard.getDealBoardComments().add(dealBoardComment);

            Comment comment = Comment.builder().
                    freeBoardComment(null).
                    dealBoardComment(dealBoardComment).
                    build();
            commentRepository.save(comment);

            dealBoardCommentRepository.save(dealBoardComment);
        } catch (Exception e){
            instantMatchingResponseDTO.setStatus("댓글 처리 도중 에러가 발생했습니다.");
        }

        /* 게시글 매칭 완료로 변경 */
        try{
            dealBoardService.modifyMatchingFinish(boardId);
        } catch (Exception e){
            instantMatchingResponseDTO.setStatus("게시글 매칭 완료로 변경 과정에서 에러가 발생했습니다.");
        }

        /* 댓글 매칭 완료로 변경 */
        try{
            dealBoardCommentService.setAdoptedComments(dealBoardComment.getD_commentId());
        } catch (Exception e)
        {
            instantMatchingResponseDTO.setStatus("댓글 매칭 완료로 변경 과정에서 에러가 발생했습니다.");
        }

        instantMatchingResponseDTO.setStatus("즉석 매칭을 성공적으로 완료했습니다.");
        instantMatchingResponseDTO.setSuccess(true);
        return instantMatchingResponseDTO;
    }
}
