package com.capstone.timepay.service.admin;

import com.capstone.timepay.controller.admin.response.board.DealBoardResponse;
import com.capstone.timepay.domain.board.Board;
import com.capstone.timepay.domain.board.BoardRepository;
import com.capstone.timepay.domain.board.BoardStatus;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardRepository;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.service.admin.dto.AdminBoardDTO;
import com.capstone.timepay.service.admin.dto.AdminDealBoardDTO;
import com.capstone.timepay.service.admin.dto.AdminFreeBoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminBoardService {

    private final BoardRepository boardRepository;
    private final DealBoardRepository dealBoardRepository;

    @Transactional
    public Page<AdminBoardDTO> getAllBoards(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<Board> boards = boardRepository.findAll(pageable);
        List<AdminBoardDTO> boardDTOs = new ArrayList<>();

        for (Board board : boards) {
            AdminBoardDTO boardDTO = new AdminBoardDTO();

            FreeBoard freeBoard = board.getFreeBoard();
            if (freeBoard != null) {
                AdminFreeBoardDTO freeBoardDTO = AdminFreeBoardDTO.toFreeBoardDTO(freeBoard);
                boardDTO.setFreeBoardDTO(freeBoardDTO);
            }

            DealBoard dealBoard = board.getDealBoard();
            if (dealBoard != null) {
                AdminDealBoardDTO dealBoardDTO = AdminDealBoardDTO.toDealBoardDTO(dealBoard);
                boardDTO.setDealBoardDTO(dealBoardDTO);
            }

            boardDTOs.add(boardDTO);
        }

        return new PageImpl<>(boardDTOs, pageable, boards.getTotalElements());
    }

    public boolean setHidden(List<Long> ids) {
        try {
            for (Long id :
                    ids) {
                Board board = boardRepository.findById(id).get();
                if (board.getDealBoard() != null) {
                    board.getDealBoard().setHidden(true);
                } else {
                    board.getFreeBoard().setHidden(true);
                }
                boardRepository.save(board);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean setStatus(List<Long> ids, BoardStatus status) {
        try {
            for (Long id :
                    ids) {
                Board board = boardRepository.findById(id).get();
                if (board.getDealBoard() != null) {
                    board.getDealBoard().setBoardStatus(status);
                }
                boardRepository.save(board);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Page<DealBoardResponse> search(Specification<DealBoard> spec, Pageable pageable) {
        Page<DealBoard> dealBoards = dealBoardRepository.findAll(spec, pageable);
        return dealBoards.map(DealBoardResponse::new);
    }
}
