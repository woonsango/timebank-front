//package com.capstone.timepay.controller.board;
//
//import com.capstone.timepay.domain.board.Board;
//import com.capstone.timepay.service.board.service.BoardService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/boards")
//@RequiredArgsConstructor
//public class BoardSearchController {
//    private final BoardService boardService;
//
//    @GetMapping("/search")
//    public List<Board> boardSearch(
//            @RequestParam(required = false) String sort)
//    {
//        return boardService.boardSearch(sort);
//    }
//}
