package com.capstone.timepay.service.board.service;

import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.freeBoard.FreeBoardRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.service.board.dto.FreeBoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FreeBoardService
{
    private final FreeBoardRepository freeBoardRepository;

    // 모든 게시물 조회
    @Transactional(readOnly = true)
    public List<FreeBoardDTO> getBoards()
    {
        List<FreeBoard> freeBoards = freeBoardRepository.findAll();
        List<FreeBoardDTO> freeBoardDTOList = new ArrayList<>();
        freeBoards.forEach(freeBoard -> freeBoardDTOList.add(FreeBoardDTO.toFreeBoardDTO(freeBoard)));
        return freeBoardDTOList;
    }

    // 개별 게시물 조회
    @Transactional(readOnly = true)
    public FreeBoardDTO getBoard(Long id)
    {
        FreeBoard freeBoard = freeBoardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다.");
        });
        FreeBoardDTO freeBoardDTO = FreeBoardDTO.toFreeBoardDTO(freeBoard);
        return freeBoardDTO;
    }


    // 게시물 작성
    @Transactional
    public FreeBoardDTO write(FreeBoardDTO freeBoardDTO)
    {
        FreeBoard freeBoard = new FreeBoard();
        freeBoard.setTitle(freeBoardDTO.getTitle());
        freeBoard.setContent(freeBoardDTO.getContent());
        freeBoard.setCategory(freeBoardDTO.getCategory());
        freeBoardRepository.save(freeBoard);
        return FreeBoardDTO.toFreeBoardDTO(freeBoard);
    }

    // 게시물 수정
    @Transactional
    public FreeBoardDTO update(Long id, FreeBoardDTO freeBoardDTO)
    {
        FreeBoard freeBoard = freeBoardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다.");
        });
        freeBoard.setTitle(freeBoardDTO.getTitle());
        freeBoard.setContent(freeBoardDTO.getContent());
        freeBoard.setCategory(freeBoardDTO.getCategory());
        return FreeBoardDTO.toFreeBoardDTO(freeBoard);
    }

    // 게시글 삭제
    @Transactional
    public void delete(Long id)
    {
        FreeBoard freeBoard = freeBoardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다!");
        });
        freeBoardRepository.deleteById(id);
    }
}
