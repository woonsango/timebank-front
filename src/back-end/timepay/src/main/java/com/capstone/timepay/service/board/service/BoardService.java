package com.capstone.timepay.service.board.service;

import com.capstone.timepay.domain.board.Board;
import com.capstone.timepay.domain.board.BoardRepository;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.service.board.dto.BoardDTO;
import com.capstone.timepay.service.board.dto.DealBoardDTO;
import com.capstone.timepay.service.board.dto.FreeBoardDTO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public List<BoardDTO> getAllBoards() {
        List<Board> boards = boardRepository.findAll();
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

        return boardDTOs;
    }

}
