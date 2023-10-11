package com.capstone.timepay.controller.board;

import com.capstone.timepay.domain.board.Board;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.service.board.service.BoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardSearchController {
    private final BoardService boardService;

    @GetMapping("/search")
    public List<Board> searchBoards(
            @RequestParam(name = "sort", defaultValue = "LATEST_FIRST") BoardService.SortType sortType,
            @RequestParam(name = "freeBoard", defaultValue = "true") boolean freeBoard,
            @RequestParam(name = "dealBoard", defaultValue = "true") boolean dealBoard,
            @RequestParam(name = "user", required = false) String user,
            @RequestParam(name = "category", required = false) String category
    )
    {
        return boardService.search(sortType, freeBoard, dealBoard, user, category);
    }
}
