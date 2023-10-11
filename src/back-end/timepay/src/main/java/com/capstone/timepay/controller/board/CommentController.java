package com.capstone.timepay.controller.board;

import com.capstone.timepay.domain.comment.CommentRepository;
import com.capstone.timepay.service.board.dto.BoardDTO;
import com.capstone.timepay.service.board.dto.CommentDTO;
import com.capstone.timepay.service.board.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    @ApiOperation("전체댓글 조회")
    public ResponseEntity<Page<CommentDTO>> getAllComments(
            @RequestParam(value = "pagingIndex", defaultValue = "0") int pagingIndex,
            @RequestParam(value = "pagingSize", defaultValue = "50") int pagingSize)
    {
        Page<CommentDTO> page = commentService.getAllComments(pagingIndex, pagingSize);
        if (page.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
