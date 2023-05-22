package com.capstone.timepay.service.board.service;

import com.capstone.timepay.domain.board.BoardRepository;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.dealRegister.DealRegisterRepository;
import com.capstone.timepay.domain.donateBoard.DonateBoard;
import com.capstone.timepay.domain.donateBoard.DonateBoardRepository;
import com.capstone.timepay.domain.organization.Organization;
import com.capstone.timepay.domain.organization.OrganizationRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.domain.userProfile.UserProfile;
import com.capstone.timepay.service.board.dto.DonateBoardDTO;
import com.capstone.timepay.service.board.dto.DonateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Printable;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonateBoardService
{
    private final DonateBoardRepository donateBoardRepository;
    private final DealRegisterRepository dealRegisterRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    @Transactional
    public DonateBoardDTO donateWrite(DonateBoardDTO donateBoardDTO, Principal principal)
    {
        Organization organization = organizationRepository.findByAccount(principal.getName()).orElse(null);

        User user = userRepository.findByOrganization(organization).orElseThrow(() -> {
            return new IllegalArgumentException("해당 유저는 존재하지 않습니다");
        });

        DonateBoard donateBoard = DonateBoard.builder()
                .title(donateBoardDTO.getTitle())
                .content(donateBoardDTO.getContent())
                .type("기부하기")
                .targetTimePay(donateBoardDTO.getTargetTimePay())
                .donateTimePay(0)
                .category(donateBoardDTO.getCategory())
                .userId(user.getUserId())
                .organizationName(user.getOrganization().getOrganizationName())
                .userNickname(user.getNickname())
                .userType("기관 회원")
                .imageURL(user.getOrganization().getImageUrl())
                .build();

        donateBoardRepository.save(donateBoard);
        return DonateBoardDTO.toDonateDTO(donateBoard);
    }

    // 모든 기부하기 게시판 확인
    @Transactional(readOnly = true)
    public Page<DonateBoardDTO> getDonateBoards(int pagingIndex, int pagingSize)
    {
        Pageable pageable = PageRequest.of(pagingIndex, pagingSize, Sort.by("createdAt").descending());
        Page<DonateBoard> donateBoards = donateBoardRepository.findAll(pageable);
        List<DonateBoardDTO> donateBoardDTOS = donateBoards.stream()
                .map(donateBoard -> DonateBoardDTO.toDonateDTO(donateBoard))
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

    public DonateBoardDTO updateDonate(Long boardId, DonateBoardDTO donateBoardDTO) {

        DonateBoard donateBoard = donateBoardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("해당 게시판이 존재하지 않습니다");
        });
        
        donateBoard.setTitle(donateBoardDTO.getTitle());
        donateBoard.setContent(donateBoardDTO.getContent());
        donateBoard.setCategory(donateBoardDTO.getCategory());
        donateBoard.setTargetTimePay(donateBoardDTO.getTargetTimePay());
        donateBoardRepository.save(donateBoard);
        return DonateBoardDTO.toDonateDTO(donateBoard);
    }

    public void deleteDonate(Long boardId)
    {
        DonateBoard donateBoard = donateBoardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("해당 게시판이 존재하지 않습니다");
        });

        donateBoardRepository.delete(donateBoard);
    }

    public void donateDonate(Long boardId, DonateDTO donateDTO, Principal principal) {
        DonateBoard donateBoard = donateBoardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("해당 게시판이 존재하지 않습니다");
        });

        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> {
            return new IllegalArgumentException("해당 유저는 존재하지 않습니다");
        });
        donateBoard.setDonateTimePay(donateBoard.getDonateTimePay() + donateDTO.getDonateTimePay());
        donateBoardRepository.save(donateBoard);
        // 나중에 유저 정보 추가
    }
}
