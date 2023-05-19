package com.capstone.timepay.service.board.service;

import com.capstone.timepay.domain.board.BoardRepository;
import com.capstone.timepay.domain.donateBoard.DonateBoard;
import com.capstone.timepay.domain.donateBoard.DonateBoardRepository;
import com.capstone.timepay.service.board.dto.DonateBoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonateBoardService
{
    private final DonateBoardRepository donateBoardRepository;
    private final DealRegisterService dealRegisterService;
    private final BoardRepository boardRepository;

    @Transactional
    public DonateBoardDTO donateWrite(DonateBoardDTO donateBoardDTO)
    {
        DonateBoard donateBoard = DonateBoard.builder()
                .title(donateBoardDTO.getTitle())
                .content(donateBoardDTO.getContent())
                .type("기부하기")
                .targetTimePay(donateBoardDTO.getTargetTimePay())
                .donateTimePay(donateBoardDTO.getDonateTimePay())
                .category(donateBoardDTO.getCategory())
                .build();
        donateBoardRepository.save(donateBoard);

        return DonateBoardDTO.toDonateDTO(donateBoard);
    }

    // 모든 기부하기 게시판 확인
    @Transactional(readOnly = true)
    public Page<DonateBoardDTO> getDonateBoards(int pagingIndex, int pagingSize)
    {
        Pageable pageable = PageRequest.of(pagingIndex, pagingSize);
        Page<DonateBoard> donateBoards = donateBoardRepository.findAll(pageable);
        List<DonateBoardDTO> donateBoardDTOS = donateBoards.stream()
                .map(DonateBoardDTO::toDonateDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(donateBoardDTOS, donateBoards.getPageable(), donateBoards.getTotalElements());
    }

    public DonateBoardDTO getDonateBoard(Long boardId)
    {
        DonateBoard donateBoard = donateBoardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("해당 게시판이 존재하지 않습니다");
        });
        return DonateBoardDTO.toDonateDTO(donateBoard);
    }
}
