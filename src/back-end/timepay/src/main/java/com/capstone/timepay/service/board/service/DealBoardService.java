package com.capstone.timepay.service.board.service;

import com.capstone.timepay.domain.board.Board;
import com.capstone.timepay.domain.board.BoardRepository;
import com.capstone.timepay.domain.board.BoardStatus;
import com.capstone.timepay.domain.dealAttatchment.DealAttatchment;
import com.capstone.timepay.domain.dealAttatchment.DealAttatchmentRepository;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardRepository;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.dealRegister.DealRegisterRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.firebase.FirebaseService;
import com.capstone.timepay.service.board.dto.DealBoardDTO;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DealBoardService
{
    private final DealBoardRepository dealBoardRepository;
    private final BoardRepository boardRepository;
    private final DealRegisterRepository dealRegisterRepository;
    private final UserRepository userRepository;
    private final FirebaseService firebaseService;
    private final DealAttatchmentRepository dealAttatchmentRepository;

    public DealBoard getId(Long id)
    {
        return dealBoardRepository.findById(id).orElse(null);
    }

    // 전체 게시물 조회
    @Transactional(readOnly = true)
    public Page<DealBoardDTO> getDealBoards(int pagingIndex, int pagingSize)
    {
        Pageable pageable;
        long totalElements = dealBoardRepository.countByIsHiddenFalse();
        if (totalElements <= pagingSize) {
            pageable = PageRequest.of(pagingIndex, (int) totalElements);
        } else {
            pageable = PageRequest.of(pagingIndex, pagingSize);
        }

//        Pageable pageable = PageRequest.of(pagingIndex, paingSize);
        Page<DealBoard> dealBoardPage = dealBoardRepository.findByIsHiddenFalse(pageable);
        List<DealBoardDTO> dealBoardDTOList = dealBoardPage.stream()
                .map(DealBoardDTO::toDealBoardDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(dealBoardDTOList, dealBoardPage.getPageable(), dealBoardPage.getTotalElements());
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

    // 도움주기 게시물 조회
    @Transactional(readOnly = true)
    public Page<DealBoardDTO> getHelperDealBoard(int pagingIndex, int paingSize)
    {
        Pageable pageable = PageRequest.of(pagingIndex, paingSize);
        Page<DealBoard> dealBoardPage = dealBoardRepository.findByType(pageable, "helper");
        List<DealBoardDTO> dealBoardDTOList = dealBoardPage.stream()
                .map(DealBoardDTO::toDealBoardDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(dealBoardDTOList, dealBoardPage.getPageable(), dealBoardPage.getTotalElements());
    }

    // 도움받기 게시물 조회
    @Transactional(readOnly = true)
    public Page<DealBoardDTO> getHelpDealBoard(int pagingIndex, int paingSize)
    {
        Pageable pageable = PageRequest.of(pagingIndex, paingSize);
        Page<DealBoard> dealBoardPage = dealBoardRepository.findByType(pageable, "help");
        List<DealBoardDTO> dealBoardDTOList = dealBoardPage.stream()
                .map(DealBoardDTO::toDealBoardDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(dealBoardDTOList, dealBoardPage.getPageable(), dealBoardPage.getTotalElements());
    }

    // 게시물 작성
    @Transactional
    public DealBoardDTO write(DealBoardDTO dealBoardDTO, String email, String type,
                              List<MultipartFile> images) throws IOException, FirebaseAuthException
    {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            return new IllegalArgumentException("해당 유저를 찾을 수 없습니다.");
        });

        List<DealAttatchment> dealAttatchments = new ArrayList<>();
        for (MultipartFile image : images)
        {
            String imageUrl = firebaseService.uploadFiles(image);
            DealAttatchment dealAttatchment = DealAttatchment.builder()
                    .imageUrl(imageUrl)
                    .build();
            dealAttatchments.add(dealAttatchment);
            dealAttatchmentRepository.save(dealAttatchment);
        }

        DealBoard dealBoard = DealBoard.builder()
                .title(dealBoardDTO.getTitle())
                .boardStatus(dealBoardDTO.getState())
                .content(dealBoardDTO.getContent())
                .category(dealBoardDTO.getCategory())
                .type(type)
                .boardStatus(BoardStatus.MATCHING_IN_PROGRESS)
                .location(dealBoardDTO.getLocation())
                .startTime(dealBoardDTO.getStartTime())
                .endTime(dealBoardDTO.getEndTime())
                .pay(dealBoardDTO.getPay())
                .isHidden(dealBoardDTO.isHidden())
                .build();

        Board board = Board.builder().
                freeBoard(null).
                dealBoard(dealBoard).
                build();
        boardRepository.save(board);

        DealRegister dealRegister = DealRegister.builder()
                .d_registerId(dealBoard.getD_boardId())
                .dealBoard(dealBoard)
                .user(user)
               .build();
               dealRegisterRepository.save(dealRegister);

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
        dealBoard.setBoardStatus(boardDto.getState());
        dealBoard.setContent(boardDto.getContent());
        dealBoard.setCategory(boardDto.getCategory());
        dealBoard.setLocation(boardDto.getLocation());
        dealBoard.setStartTime(boardDto.getEndTime());
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

    @Transactional
    public DealBoardDTO modifyMatchingFinish(Long boardId)
    {
        DealBoard dealBoard = dealBoardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다");
        });

        dealBoard.setBoardStatus(BoardStatus.MATCHING_COMPLETE);
        return DealBoardDTO.toDealBoardDTO(dealBoard);
    }

    @Transactional
    public DealBoardDTO modifyActivityFinsih(Long boardId)
    {
        DealBoard dealBoard = dealBoardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다");
        });

        dealBoard.setBoardStatus(BoardStatus.ACTIVITY_COMPLETE);
        return DealBoardDTO.toDealBoardDTO(dealBoard);
    }

    @Transactional
    public void insertComment(Long boardId, DealBoardComment dealBoardComment)
    {
        DealBoard dealBoard = dealBoardRepository.findById(boardId).orElse(null);
        dealBoard.getDealBoardComments().add(dealBoardComment);
    }
}
