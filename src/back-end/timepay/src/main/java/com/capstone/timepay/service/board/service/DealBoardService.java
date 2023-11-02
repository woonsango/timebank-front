package com.capstone.timepay.service.board.service;

import com.capstone.timepay.controller.admin.response.board.DealBoardResponse;
import com.capstone.timepay.domain.board.Board;
import com.capstone.timepay.domain.board.BoardRepository;
import com.capstone.timepay.domain.board.BoardStatus;
import com.capstone.timepay.domain.comment.CommentRepository;
import com.capstone.timepay.domain.dealAttatchment.DealAttatchment;
import com.capstone.timepay.domain.dealAttatchment.DealAttatchmentRepository;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardRepository;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReport;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReportRepository;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.dealRegister.DealRegisterRepository;
import com.capstone.timepay.domain.organization.Organization;
import com.capstone.timepay.domain.organization.OrganizationRepository;
import com.capstone.timepay.domain.report.ReportRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.firebase.FirebaseService;
import com.capstone.timepay.service.board.dto.DealBoardDTO;
import com.capstone.timepay.service.organization.OrganizationManageService;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
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
    private final OrganizationManageService organizationManageService;
    private final CommentRepository commentRepository;
    private final ReportRepository reportRepository;
    private final OrganizationRepository organizationRepository;
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
    public Page<DealBoardDTO> getHelperDealBoard(int pagingIndex, int pagingSize)
    {
        Pageable pageable = PageRequest.of(pagingIndex, pagingSize);
        Page<DealBoard> dealBoardPage = dealBoardRepository.findByType(pageable, "helper");
        List<DealBoardDTO> dealBoardDTOList = dealBoardPage.stream()
                .map(DealBoardDTO::toDealBoardDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(dealBoardDTOList, dealBoardPage.getPageable(), dealBoardPage.getTotalElements());
    }

    // 도움요청 게시물 조회
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
    public DealBoardDTO write(DealBoardDTO dealBoardDTO, Principal principal, String type,
                              List<MultipartFile> images) throws IOException, FirebaseAuthException
    {
        User user = userRepository.findByEmail(principal.getName()).orElse(null);

        if (user == null)
        {
            Organization organization = organizationRepository.findByAccount(principal.getName()).orElse(null);
            if (organization == null)
                throw new IllegalArgumentException("유저가 존재하지 않습니다");
            user = userRepository.findByOrganization(organization).orElseThrow(() -> {
               return new IllegalArgumentException("그냥 유저가 존재하지 않습니다");
            });
        }

        if (images != null) {
            List<DealAttatchment> dealAttatchments = new ArrayList<>();
            for (MultipartFile image : images) {
                String imageUrl = firebaseService.uploadFiles(image);
                DealAttatchment dealAttatchment = DealAttatchment.builder()
                        .imageUrl(imageUrl)
                        .build();
                dealAttatchments.add(dealAttatchment);
                dealAttatchmentRepository.save(dealAttatchment);
            }
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
                .isAuto(dealBoardDTO.isAuto())
                .volunteerPeople(dealBoardDTO.getVolunteerPeople())
                .volunteerTime(dealBoardDTO.getVolunteerTime())
                .isVolunteer(dealBoardDTO.isVolunteer())
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
        return DealBoardDTO.toDealBoardAndUserDTO(dealBoard, user);
    }

    @Transactional
    public DealBoardDTO helperWrite(DealBoardDTO dealBoardDTO, Principal principal,
                                    String type, List<MultipartFile> images) throws IOException, FirebaseAuthException
    {
        User user = userRepository.findByEmail(principal.getName()).orElse(null);

        if (user == null)
        {
            Organization organization = organizationRepository.findByAccount(principal.getName()).orElse(null);
            if (organization == null)
                throw new IllegalArgumentException("유저가 존재하지 않습니다");
            user = userRepository.findByOrganization(organization).orElseThrow(() -> {
                return new IllegalArgumentException("그냥 유저가 존재하지 않습니다");
            });
        }

        if (images != null) {
            List<DealAttatchment> dealAttatchments = new ArrayList<>();
            for (MultipartFile image : images) {
                String imageUrl = firebaseService.uploadFiles(image);
                DealAttatchment dealAttatchment = DealAttatchment.builder()
                        .imageUrl(imageUrl)
                        .build();
                dealAttatchments.add(dealAttatchment);
                dealAttatchmentRepository.save(dealAttatchment);
            }
        }

        DealBoard dealBoard = DealBoard.builder()
                .title(dealBoardDTO.getTitle())
                .content(dealBoardDTO.getContent())
                .category(dealBoardDTO.getCategory())
                .type(type)
                .location(dealBoardDTO.getLocation())
                .startTime(dealBoardDTO.getStartTime())
                .endTime(dealBoardDTO.getEndTime())
                .isHidden(dealBoardDTO.isHidden())
                .isVolunteer(dealBoardDTO.isVolunteer())
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
        return DealBoardDTO.toDealBoardAndUserDTO(dealBoard, user);
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
        dealBoard.setStartTime(boardDto.getStartTime());
        dealBoard.setEndTime(boardDto.getEndTime());
        dealBoard.setPay(boardDto.getPay());
        dealBoard.setHidden(boardDto.isHidden());
        dealBoard.setAuto(boardDto.isAuto());
        dealBoard.setVolunteerPeople(boardDto.getVolunteerPeople());
        dealBoard.setUpdatedAt(LocalDateTime.now());

        return DealBoardDTO.toDealBoardDTO(dealBoard);
    }

    @Transactional
    public void delete(Long id) {
        // 매개변수 id를 기반으로, 게시글이 존재하는지 먼저 찾음
        DealBoard dealBoard = dealBoardRepository.findById(id).orElseThrow(() -> {
                return new IllegalArgumentException("게시판을 찾지 못했습니다");
        });

        if (dealBoard != null)
        {
            // dealBoard 안의 모든 Comment 테이블 데이터 삭제
            List<DealBoardComment> dealBoardComments = dealBoard.getDealBoardComments();
            for (DealBoardComment dealBoardComment : dealBoardComments)
            {
                commentRepository.delete(commentRepository.findByDealBoardComment(dealBoardComment));
            }

            // dealBoard의 Board 테이블 데이터 삭제
            List<DealRegister> dealRegisters = dealBoard.getDealRegisters();
            List<Board> boards = boardRepository.findByDealBoardIn(
                    dealBoardRepository.findByDealRegistersIn(dealRegisters));
            for (Board board : boards) {
                boardRepository.delete(board);
            }

            // dealBoard의 Report 테이블 데이터 삭제
            List<DealBoardReport> dealBoardReports = dealBoard.getDealBoardReports();
            for (DealBoardReport dealBoardReport : dealBoardReports) {
                reportRepository.delete(reportRepository.findByDealBoardReport(dealBoardReport));
            }

            dealBoardRepository.delete(dealBoard);
        }
        else
        {
            System.out.println("존재하지 않는 게시판입니다");
        }
    }

    @Transactional
    public DealBoardDTO modifyMatchingFinish(Long boardId)
    {
        DealBoard dealBoard = dealBoardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다");
        });

        dealBoard.setBoardStatus(BoardStatus.MATCHING_COMPLETE);
        dealBoardRepository.save(dealBoard);
        return DealBoardDTO.toDealBoardDTO(dealBoard);
    }

    @Transactional
    public DealBoardDTO modifyActivityFinish(Long boardId)
    {
        DealBoard dealBoard = dealBoardRepository.findById(boardId).orElseThrow(() -> {
            return new IllegalArgumentException("Board Id를 찾을 수 없습니다");
        });

        dealBoard.setBoardStatus(BoardStatus.ACTIVITY_COMPLETE);
        if (dealBoard.isVolunteer())
            organizationManageService.activityComplete(boardId); // 준원님 요청

        /* 타임페이 교환하는 로직 */

        int timePay = 0;
        int volunteerPeople = 0;

        // 기관, 일반 유저
        if (dealBoard.getDealRegisters().get(0).getUser().getOrganization() != null) {
            timePay = dealBoard.getDealRegisters().get(0).getUser().getOrganization().getTimepay();
        } else {
            timePay = dealBoard.getDealRegisters().get(0).getUser().getUserProfile().getTimepay();
        }
        // 해당 게시글의 활동시간
        int activityStartTime = (dealBoard.getStartTime().getHour() * 60) + dealBoard.getStartTime().getMinute();
        int activityEndTime = (dealBoard.getEndTime().getHour() * 60) + dealBoard.getEndTime().getMinute();
        int activityTime = activityEndTime - activityStartTime;
        // 해당 게시글의 인원수
        List<DealBoardComment> dealBoardCommentPeople = dealBoard.getDealBoardComments();
        for (DealBoardComment dealBoardComment : dealBoardCommentPeople)
        {
            if (dealBoardComment.isAdopted())
                volunteerPeople++;
        }

        // 작성자 타임페이 차감
        if (dealBoard.getDealRegisters().get(0).getUser().getOrganization() != null) {
            dealBoard.getDealRegisters().get(0).getUser().getOrganization().setTimepay(timePay - (activityTime * volunteerPeople));
        } else {
            dealBoard.getDealRegisters().get(0).getUser().getUserProfile().setTimepay(timePay - (activityTime * volunteerPeople));
        }

        // 활동한 유저들 타임페이 증가시키는 로직
        List<DealBoardComment> dealBoardComments = dealBoard.getDealBoardComments();
        for (DealBoardComment dealBoardComment : dealBoardComments)
        {
            if (dealBoardComment.isAdopted()) {
                int userTimePay = dealBoardComment.getUser().getUserProfile().getTimepay();
                dealBoardComment.getUser().getUserProfile().setTimepay(userTimePay + activityTime);
            }
        }

        dealBoardRepository.save(dealBoard);
        return DealBoardDTO.toDealBoardDTO(dealBoard);
    }

    @Transactional
    public void insertComment(Long boardId, DealBoardComment dealBoardComment)
    {
        DealBoard dealBoard = dealBoardRepository.findById(boardId).orElse(null);
        dealBoard.getDealBoardComments().add(dealBoardComment);
    }

    public Page<DealBoardResponse> search(Specification<DealBoard> spec, Pageable pageable) {
        Page<DealBoard> dealBoards=  dealBoardRepository.findAll(spec, pageable);
        return dealBoards.map(DealBoardResponse::new);
    }
}
