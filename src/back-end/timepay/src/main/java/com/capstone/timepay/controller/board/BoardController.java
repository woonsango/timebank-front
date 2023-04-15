package com.capstone.timepay.controller.board;

import com.capstone.timepay.service.board.dto.BoardDTO;
import com.capstone.timepay.service.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public List<BoardDTO> getAllBoards() {
        return boardService.getAllBoards();
    }
}
