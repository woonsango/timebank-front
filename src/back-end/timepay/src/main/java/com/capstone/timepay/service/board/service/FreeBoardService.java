package com.capstone.timepay.service.board.service;

import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.freeBoard.FreeBoardRepository;
import com.capstone.timepay.service.board.dto.FreeBoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// DTO -> Entity
// Entity - > DTO

@Service
@RequiredArgsConstructor
public class FreeBoardService {

    private final FreeBoardRepository freeBoardRepository;

    public void save(FreeBoardDTO freeBoardDTO)
    {
        FreeBoard freeBoard = FreeBoard.toSaveFreeEntity(freeBoardDTO);
        freeBoardRepository.save(freeBoard);
    }

    public List<FreeBoardDTO> freeBoardFindAll()
    {
        List<FreeBoard> freeBoardList = freeBoardRepository.findAll();
        List<FreeBoardDTO> freeBoardDTOList = new ArrayList<>();
        for (FreeBoard freeBoard : freeBoardList)
        {
            freeBoardDTOList.add(FreeBoardDTO.toFreeBoardDTO(freeBoard));
        }
        return freeBoardDTOList;
    }

    @Transactional
    public void updateHits(Long id)
    {
        freeBoardRepository.updateHits(id);
    }

    public FreeBoardDTO findById(Long id)
    {
        Optional<FreeBoard> freeBoardOptional = freeBoardRepository.findById(id);
        if (freeBoardOptional.isPresent())
        {
            FreeBoard freeBoard = freeBoardOptional.get();
            FreeBoardDTO boardDTO = FreeBoardDTO.toFreeBoardDTO(freeBoard);
            return boardDTO;
        } else {
            return null;
        }
    }
}
