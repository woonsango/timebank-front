package com.capstone.timepay.service.board.service;

import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardRepository;
import com.capstone.timepay.service.board.dto.DealBoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DealBoardService
{
    private final DealBoardRepository dealBoardRepository;

    // 전체 게시물 조회
    @Transactional(readOnly = true)
    public List<DealBoardDTO> getDealBoards()
    {
        List<DealBoard> dealBoards = dealBoardRepository.findAll();
        List<DealBoardDTO> dealBoardDTOS = new ArrayList<>();
        dealBoards.forEach(s -> dealBoardDTOS.add(DealBoardDTO.toDealBoardDTO(s)));
        return dealBoardDTOS;
    }

    // 개별 게시물 조회
    @Transactional(readOnly = true)
    public DealBoardDTO getDealBoard(Long id)
    {
        DealBoard dealBoard = dealBoardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다");
        });
        DealBoardDTO dealBoardDTO = DealBoardDTO.toDealBoardDTO(dealBoard);
        return dealBoardDTO;
    }

    // 숨김처리 안된 게시물만 조회
    @Transactional(readOnly = true)
    public List<DealBoardDTO> getGoodBoard()
    {
        List<DealBoard> dealBoards = dealBoardRepository.findAllByIsHiddenFalse();
        List<DealBoardDTO> dealBoardDTOList = new ArrayList<>();
        dealBoards.forEach(dealBoard -> dealBoardDTOList.add(DealBoardDTO.toDealBoardDTO(dealBoard)));
        return dealBoardDTOList;
    }

    // 숨김처리된 게시물만 조회
    @Transactional(readOnly = true)
    public List<DealBoardDTO> getBadBoard()
    {
        List<DealBoard> dealBoards = dealBoardRepository.findAAByIsHiddenTrue();
        List<DealBoardDTO> dealBoardDTOList = new ArrayList<>();
        dealBoards.forEach(dealBoard -> dealBoardDTOList.add(DealBoardDTO.toDealBoardDTO(dealBoard)));
        return dealBoardDTOList;
    }

    // 게시물 작성
    @Transactional
    public DealBoardDTO write(DealBoardDTO dealBoardDTO)
    {
        DealBoard dealBoard = new DealBoard();
        dealBoard.setTitle(dealBoardDTO.getTitle());
        dealBoard.setState(dealBoardDTO.getState());
        dealBoard.setContent(dealBoardDTO.getContent());
        dealBoard.setCategory(dealBoardDTO.getCategory());
        dealBoard.setLocation(dealBoardDTO.getLocation());
        dealBoard.setStartTime(dealBoardDTO.getEndTime());
        dealBoard.setPay(dealBoardDTO.getPay());
        dealBoard.setCreatedAt(LocalDateTime.now());
        dealBoard.setUpdatedAt(LocalDateTime.now());
        dealBoard.setHidden(dealBoardDTO.isHidden());
        dealBoardRepository.save(dealBoard);
        return DealBoardDTO.toDealBoardDTO(dealBoard);
    }

    // 게시물 수정
    @Transactional
    public DealBoardDTO update(Long id, DealBoardDTO boardDto) {
        DealBoard dealBoard = dealBoardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다");
        });

        dealBoard.setTitle(boardDto.getTitle());
        dealBoard.setContent(boardDto.getContent());
        dealBoard.setState(boardDto.getState());
        dealBoard.setCategory(boardDto.getCategory());
        dealBoard.setLocation(boardDto.getLocation());
        dealBoard.setStartTime(boardDto.getStartTime());
        dealBoard.setEndTime(boardDto.getEndTime());
        dealBoard.setPay(boardDto.getPay());
        dealBoard.setHidden(boardDto.isHidden());

        return DealBoardDTO.toDealBoardDTO(dealBoard);
    }

    @Transactional
    public void delete(Long id) {
        // 매개변수 id를 기반으로, 게시글이 존재하는지 먼저 찾음
        // 게시글이 없으면 오류 처리
        DealBoard dealBoard = dealBoardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다!");
        });

        // 게시글이 있는 경우 삭제처리
        dealBoardRepository.deleteById(id);

    }
}
