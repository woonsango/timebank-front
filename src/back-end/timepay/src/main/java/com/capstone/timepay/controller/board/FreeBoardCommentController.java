package com.capstone.timepay.controller.board;

import com.capstone.timepay.domain.annotation.Response;
import com.capstone.timepay.domain.user.TestUser;
import com.capstone.timepay.domain.user.TestUserRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.service.board.dto.FreeBoardCommentDTO;
import com.capstone.timepay.service.board.service.FreeBoardCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/freeBoard")
public class FreeBoardCommentController {

    private final FreeBoardCommentService freeBoardCommentService;
    private final TestUserRepository testUserRepository;

    // 댓글 작성
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/comment/{boardId}")
    public Response writeComment(@PathVariable("boardId") Long boardId, @RequestBody FreeBoardCommentDTO freeBoardCommentDTO)
    {
        // TODO: 추후 User 정보는 세션을 통해 주고받도록 수정
        TestUser testUser = testUserRepository.findById(Long.valueOf(1)).get();
        return new Response("SUCCESS", "댓글 작성", freeBoardCommentService.writeComment(boardId, freeBoardCommentDTO, testUser));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/comment/{boardId}")
    public Response getComments(@PathVariable("boardId") Long boardId)
    {
        return new Response("성공", "댓글을 불러왔습니다.", freeBoardCommentService.getComments(boardId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/comment/{boardId}/{commentId}")
    public Response deleteComment(@PathVariable("boardId") Long boardId, @PathVariable("commentId") Long commentId)
    {
        // TODO: 후 JWT 로그인 기능을 추가하고나서, 세션에 로그인된 유저와 댓글 작성자를 비교해서,
        //  맞으면 삭
        return new Response("SUCCESS", "댓글 삭제", freeBoardCommentService.deleteComment(commentId));
    }
}
