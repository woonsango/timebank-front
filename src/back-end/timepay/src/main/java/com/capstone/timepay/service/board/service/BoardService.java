package com.capstone.timepay.service.board.service;

import com.capstone.timepay.domain.board.Board;
import com.capstone.timepay.domain.board.BoardRepository;
import com.capstone.timepay.domain.board.BoardSearch;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.service.board.dto.BoardDTO;
import com.capstone.timepay.service.board.dto.DealBoardDTO;
import com.capstone.timepay.service.board.dto.FreeBoardDTO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Page<BoardDTO> getAllBoards(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<Board> boards = boardRepository.findAll(pageable);
        List<BoardDTO> boardDTOs = new ArrayList<>();

        for (Board board : boards) {
            BoardDTO boardDTO = new BoardDTO();

            FreeBoard freeBoard = board.getFreeBoard();
            if (freeBoard != null) {
                FreeBoardDTO freeBoardDTO = FreeBoardDTO.toFreeBoardDTO(freeBoard);
                boardDTO.setFreeBoardDTO(freeBoardDTO);
            }

            DealBoard dealBoard = board.getDealBoard();
            if (dealBoard != null) {
                DealBoardDTO dealBoardDTO = DealBoardDTO.toDealBoardDTO(dealBoard);
                boardDTO.setDealBoardDTO(dealBoardDTO);
            }

            boardDTOs.add(boardDTO);
        }

        return new PageImpl<>(boardDTOs, pageable, boards.getTotalElements());
    }

//    public List<Board> boardSearch(
//            @RequestParam(required = false) String sort)
//    {
//        Specification<Board> spec = Specification.where(null);
//
//        if (sort != null) {
//            if (sort.equals("latest")) {
//                spec = spec.and(BoardSearch.latest());
//            } else if (sort.equals("oldest")) {
//                spec = spec.and(BoardSearch.oldest());
//            }
//        }
//
//        return boardRepository.findAll(spec);
//    }

}
