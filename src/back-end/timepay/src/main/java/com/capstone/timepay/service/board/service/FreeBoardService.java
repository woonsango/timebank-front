package com.capstone.timepay.service.board.service;

import com.capstone.timepay.domain.board.Board;
import com.capstone.timepay.domain.board.BoardRepository;
import com.capstone.timepay.domain.freeAttatchment.FreeAttatchment;
import com.capstone.timepay.domain.freeAttatchment.FreeAttatchmentRepository;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.freeBoard.FreeBoardRepository;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import com.capstone.timepay.domain.freeRegister.FreeRegister;
import com.capstone.timepay.domain.freeRegister.FreeRegisterRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.firebase.FirebaseService;
import com.capstone.timepay.service.board.dto.FreeBoardDTO;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FreeBoardService
{
    private final FreeBoardRepository freeBoardRepository;
    private final BoardRepository boardRepository;
    private final FreeRegisterRepository freeRegisterRepository;
    private final UserRepository userRepository;
    private final FirebaseService firebaseService;
    private final FreeAttatchmentRepository freeAttatchmentRepository;

    public FreeBoard getId(Long id)
    {
        return freeBoardRepository.findById(id).orElse(null);
    }

    // 모든 게시물 조회
    @Transactional
    public Page<FreeBoardDTO> getBoards(int pagingIndex, int pagingSize)
    {
        Pageable pageable = PageRequest.of(pagingIndex, pagingSize);
        Page<FreeBoard> freeBoardPage = freeBoardRepository.findByIsHiddenFalse(pageable);
        List<FreeBoardDTO> freeBoardDTOList = freeBoardPage.stream()
                .map(FreeBoardDTO::toFreeBoardDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(freeBoardDTOList, freeBoardPage.getPageable(), freeBoardPage.getTotalElements());
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
    public FreeBoardDTO write(FreeBoardDTO freeBoardDTO,
                              String email,
                              List<MultipartFile> images) throws IOException, FirebaseAuthException
    {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            return new IllegalArgumentException("해당 유저를 찾을 수 없습니다.");
        });

        List<FreeAttatchment> freeAttatchments = new ArrayList<>();
        for (MultipartFile image : images)
        {
            String imageUrl = firebaseService.uploadFiles(image);
            FreeAttatchment freeAttatchment = FreeAttatchment.builder()
                    .imageUrl(imageUrl)
                    .build();
            freeAttatchments.add(freeAttatchment);
            freeAttatchmentRepository.save(freeAttatchment);
        }

        FreeBoard freeBoard = FreeBoard.builder()
                .title(freeBoardDTO.getTitle())
                .content(freeBoardDTO.getContent())
                .category(freeBoardDTO.getCategory())
                .isHidden(freeBoardDTO.isHidden())
                .freeAttatchments(freeAttatchments)
                .build();
        freeBoardRepository.save(freeBoard);

        Board board = Board.builder().
                freeBoard(freeBoard).
                dealBoard(null).
                build();
       boardRepository.save(board);

        FreeRegister freeRegister = FreeRegister.builder().
                f_registerId(freeBoard.getF_boardId()).
                freeBoard(freeBoard).
                user(user).
                build();
        freeRegisterRepository.save(freeRegister);


        return FreeBoardDTO.toFreeBoardDTO(freeBoard);
    }

    // 게시물 수정
    @Transactional
    public FreeBoardDTO update(Long id, FreeBoardDTO freeBoardDTO)
    {
        FreeBoard freeBoard = freeBoardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다.");
        });
        freeBoard = FreeBoard.builder()
                .title(freeBoardDTO.getTitle())
                .content(freeBoardDTO.getContent())
                .category(freeBoardDTO.getCategory())
                .isHidden(freeBoardDTO.isHidden())
                .build();
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

    @Transactional
    public void insertComment(Long boardId, FreeBoardComment freeBoardComment)
    {
        FreeBoard freeBoard = freeBoardRepository.findById(boardId).orElse(null);
        freeBoard.getFreeBoardComments().add(freeBoardComment);
    }
}
