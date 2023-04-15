package com.capstone.timepay.controller.board;

import com.capstone.timepay.service.board.dto.BoardDTO;
import com.capstone.timepay.service.board.service.BoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    @ApiOperation("전체게시판 조회")
    public ResponseEntity<Page<BoardDTO>> getAllBoards(
            @RequestParam(value = "pagingIndex", defaultValue = "0") int pagingIndex,
            @RequestParam(value = "pagingSize", defaultValue = "50") int pagingSize)
    {
        Page<BoardDTO> page = boardService.getAllBoards(pagingIndex, pagingSize);
        if (page.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
