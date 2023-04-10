package com.capstone.timepay.service.admin;

import com.capstone.timepay.controller.admin.response.userManage.*;
import com.capstone.timepay.domain.board.Board;
import com.capstone.timepay.domain.board.BoardRepository;
import com.capstone.timepay.domain.comment.Comment;
import com.capstone.timepay.domain.comment.CommentRepository;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardCommentRepository;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReport;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReportRepository;
import com.capstone.timepay.domain.dealCommentReport.DealCommentReport;
import com.capstone.timepay.domain.dealCommentReport.DealCommentReportRepository;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.dealRegister.DealRegisterRepository;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardCommentRepository;
import com.capstone.timepay.domain.freeBoardReport.FreeBoardReport;
import com.capstone.timepay.domain.freeBoardReport.FreeBoardReportRepository;
import com.capstone.timepay.domain.freeCommentReport.FreeCommentReport;
import com.capstone.timepay.domain.freeCommentReport.FreeCommentReportRepository;
import com.capstone.timepay.domain.freeRegister.FreeRegister;
import com.capstone.timepay.domain.freeRegister.FreeRegisterRepository;
import com.capstone.timepay.domain.report.Report;
import com.capstone.timepay.domain.report.ReportRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.domain.userProfile.UserProfileRepository;
import com.capstone.timepay.service.admin.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserManageService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final FreeRegisterRepository freeRegisterRepository;
    private final DealRegisterRepository dealRegisterRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final ReportRepository reportRepository;
    private final DealBoardCommentRepository dealBoardCommentRepository;
    private final FreeBoardCommentRepository freeBoardCommentRepository;
    private final DealBoardReportRepository dealBoardReportRepository;
    private final DealCommentReportRepository dealCommentReportRepository;
    private final FreeBoardReportRepository freeBoardReportRepository;
    private final FreeCommentReportRepository freeCommentReportRepository;

    public List<User> getAllUserList(int pageIndex, int pageSize){
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        return userRepository.findAll(pageable).getContent();
    }

    public List<MainResponse> showAllUserList(int pageIndex, int pageSize) {

        return getAllUserList(pageIndex, pageSize).stream()
                    .map(user -> MainResponse.builder()
                        .userId(user.getUserId())
                        .userName(user.getName())
                        .nickName(user.getNickname())
                        .sex(user.getSex())
                        .birth(user.getBirthday())
                        .region(user.getLocation())
                        .timepay(user.getUserProfile().getTimepay()) // 확인 필요
                        .build())
                    .sorted(Comparator.comparing(MainResponse::getUserId))
                    .collect(Collectors.toList());
    }

    public List<MainResponse> showAllUserListByName(Long userId, String value) {

        List<User> users = new ArrayList<>();

        if(!ObjectUtils.isEmpty(userId) && !ObjectUtils.isEmpty(value)){
            User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
            if(!user.getName().equals(value))
                throw new IllegalArgumentException("존재하지 않는 회원입니다.");
            users.add(user);
        }
        else if(!ObjectUtils.isEmpty(userId)){
            User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
            users.add(user);
        }
        else if(!ObjectUtils.isEmpty(value)){
            users = userRepository.findAllByName(value).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
        }
        else{
            users = userRepository.findAll();
        }

        return users.stream()
                .map(user -> MainResponse.builder()
                        .userId(user.getUserId())
                        .userName(user.getName())
                        .nickName(user.getNickname())
                        .sex(user.getSex())
                        .birth(user.getBirthday())
                        .region(user.getLocation())
                        .timepay(user.getUserProfile().getTimepay()) // 확인 필요
                        .build())
                .sorted(Comparator.comparing(MainResponse::getUserId))
                .collect(Collectors.toList());
    }
    public List<MainResponse> showAllUserListByEmail(Long userId, String query) {

        List<User> users = new ArrayList<>();

        if(!ObjectUtils.isEmpty(userId) && !ObjectUtils.isEmpty(query)){
            User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
            if(!user.getEmail().equals(query))
                throw new IllegalArgumentException("존재하지 않는 회원입니다.");
            users.add(user);
        }
        else if(!ObjectUtils.isEmpty(userId)){
            User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
            users.add(user);
        }
        else if(!ObjectUtils.isEmpty(query)){
            users = userRepository.findAllByEmail(query).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
        }
        else{
            users = userRepository.findAll();
        }

        return users.stream()
                .map(user -> MainResponse.builder()
                        .userId(user.getUserId())
                        .userName(user.getName())
                        .nickName(user.getNickname())
                        .sex(user.getSex())
                        .birth(user.getBirthday())
                        .region(user.getLocation())
                        .timepay(user.getUserProfile().getTimepay()) // 확인 필요
                        .build())
                .sorted(Comparator.comparing(MainResponse::getUserId))
                .collect(Collectors.toList());
    }
    public List<MainResponse> showAllUserListByNickname(Long userId, String query) {

        List<User> users = new ArrayList<>();

        if(!ObjectUtils.isEmpty(userId) && !ObjectUtils.isEmpty(query)){
            User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
            if(!user.getNickname().equals(query))
                throw new IllegalArgumentException("존재하지 않는 회원입니다.");
            users.add(user);
        }
        else if(!ObjectUtils.isEmpty(userId)){
            User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
            users.add(user);
        }
        else if(!ObjectUtils.isEmpty(query)){
            users = userRepository.findAllByNickname(query).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
        }
        else{
            users = userRepository.findAll();
        }

        return users.stream()
                .map(user -> MainResponse.builder()
                        .userId(user.getUserId())
                        .userName(user.getName())
                        .nickName(user.getNickname())
                        .sex(user.getSex())
                        .birth(user.getBirthday())
                        .region(user.getLocation())
                        .timepay(user.getUserProfile().getTimepay()) // 확인 필요
                        .build())
                .sorted(Comparator.comparing(MainResponse::getUserId))
                .collect(Collectors.toList());
    }

    public void updateUserInfo(UserUpdateDto userInfo) {

        User user = userRepository.findById(userInfo.getUserId()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
        user.updateNickname(userInfo.getNickname());
        user.updateBirth(userInfo.getBirth());
        user.updateName(userInfo.getName());
        user.updateLocation(userInfo.getRegion());

        log.info(user.getName());
        log.info(user.getLocation());
        log.info(user.getNickname());
        log.info(user.getBirthday().toString());

        userRepository.save(user);

    }

    public UserProfileResponse showUserProfile(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));

        return new UserProfileResponse(user.getUserProfile().getImageUrl());

    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void registerBlacklist(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
        user.registerBlacklist();

        userRepository.save(user);

    }

    public ActivityListDto getActivityList(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));


        // 1. 활동목록 - 게시글 활동 목록
        List<BoardActivityDto> boardActivityDtos = new ArrayList<>();
        List<FreeRegister> freeRegisters = freeRegisterRepository.findAllById(Collections.singleton(userId));
        for(FreeRegister element : freeRegisters){
            Board board = boardRepository.findByfBoardId(element.getFreeBoard().getF_boardId()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
            BoardActivityDto boardActivityDto = BoardActivityDto.builder()
                    .boardId(board.getBoardId())
                    .createdAt(element.getFreeBoard().getCreatedAt())
                    .boardType(element.getFreeBoard().getCategory())
                    .boardState(element.getFreeBoard().getCategory())
                    .startTime(element.getFreeBoard().getCreatedAt())
                    .endTime(element.getFreeBoard().getCreatedAt())
                    .actualStartTime(element.getFreeBoard().getCreatedAt())
                    .actualEndTime(element.getFreeBoard().getCreatedAt())
                    .title(element.getFreeBoard().getTitle())
                    .build();
            boardActivityDtos.add(boardActivityDto);    // 자유 게시판이라 필요 없는 데이터는 생성 시간으로 채움
        }

        List<DealRegister> dealRegisters = dealRegisterRepository.findAllById(Collections.singleton(userId));
        for(DealRegister element : dealRegisters){
            Board board = boardRepository.findByfBoardId(element.getDealBoard().getD_boardId()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
            BoardActivityDto boardActivityDto = BoardActivityDto.builder()
                    .boardId(board.getBoardId())
                    .createdAt(element.getDealBoard().getCreatedAt())
                    .boardType(element.getDealBoard().getCategory()) // 일단 카테고리로 넣어놓음
                    .boardState(element.getDealBoard().getState())
                    .startTime(element.getDealBoard().getStartTime())
                    .endTime(element.getDealBoard().getEndTime())
                    .actualStartTime(element.getDealBoard().getStartTime()) // 일단 시작시간으로 넣어놓음
                    .actualEndTime(element.getDealBoard().getEndTime()) // 일단 종료시간으로 넣어놓음
                    .title(element.getDealBoard().getTitle())
                    .build();
            boardActivityDtos.add(boardActivityDto);
        }

        // 2. 활동목록 - 댓글 활동 목록
        List<CommentActivityDto> commentActivityDtos = new ArrayList<>();
        List<DealBoardComment> dComments = dealBoardCommentRepository.findAllByUser(user);
        for(DealBoardComment element : dComments){
            Comment comment = commentRepository.findBydCommentId(element.getD_commentId());
            CommentActivityDto commentActivityDto = CommentActivityDto.builder()
                    .commentId(comment.getCommentId())
                    .createdAt(element.getCreatedAt())
                    .isAdopted(element.isAdopted())
                    .isApplied(element.isApplied())
                    .content(element.getContent())
                    .build();
            commentActivityDtos.add(commentActivityDto);
        }
        List<FreeBoardComment> fComments = freeBoardCommentRepository.findAllByUser(user);
        for(FreeBoardComment element : fComments){
            Comment comment = commentRepository.findByfCommentId(element.getF_commentId());
            CommentActivityDto commentActivityDto = CommentActivityDto.builder()
                    .commentId(comment.getCommentId())
                    .createdAt(element.getCreatedAt())
                    .isAdopted(false)
                    .isApplied(false)
                    .content(element.getContent())
                    .build();
            commentActivityDtos.add(commentActivityDto);
        }

        // 3. 활동목록 - 신고 한 내역 + 신고 받은 내역
        List<ReportActivityDto> reports = new ArrayList<>();
        List<ReceivedReportDto> receivedReports = new ArrayList<>();

        // 3.1 자유 게시글 신고
        List<FreeBoardReport> fbReports = freeBoardReportRepository.findAllByUser(user);

        List<Long> fBoardIds = freeRegisterRepository.findAllByUser(user) // 해당 유저가 작성한 모든 게시글의 id 값을 가지고 있는 리스트
                .stream()
                .map(freeRegister -> freeRegister.getFreeBoard().getF_boardId())
                .collect(Collectors.toList());

        for(FreeBoardReport element : fbReports){
            // 신고 한 내역
            Report report = reportRepository.findByfBoardReportId(element.getFb_reportId());
            FreeRegister freeRegister = freeRegisterRepository.findByFreeBoard(element.getFreeBoard()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
            ReportActivityDto reportDto = ReportActivityDto.builder()
                    .reportId(report.getReportId())
                    .reportedName(freeRegister.getUser().getName()) // User의 Name 보냄 (Nickname x)
                    .reportedId(freeRegister.getUser().getUserId()) // User의 UserId 보냄 ( UID, Token 등 수정 필요 )
                    .reportReason(element.getContent())
                    .boardId(element.getFreeBoard().getF_boardId())
                    .commentId(0L)
                    .build();
            reports.add(reportDto);

            // 신고 받은 내역
            if(fBoardIds.contains(element.getFreeBoard().getF_boardId())){
                ReceivedReportDto receivedReportDto = ReceivedReportDto.builder()
                        .reportId(report.getReportId())
                        .reporterName(element.getUser().getName()) // User의 Name 보냄 (Nickname x)
                        .reporterId(element.getUser().getUserId()) // User의 UserId 보냄 ( UID, Token 등 수정 필요 )
                        .reportReason(element.getContent())
                        .boardId(element.getFreeBoard().getF_boardId())
                        .commentId(0L)
                        .build();
                receivedReports.add(receivedReportDto);
            }
        }

        // 3.2 거래 게시글 신고
        List<DealBoardReport> dbReports = dealBoardReportRepository.findAllByUser(user);

        List<Long> dBoardIds = dealRegisterRepository.findAllByUser(user) // 해당 유저가 작성한 모든 게시글의 id 값을 가지고 있는 리스트
                .stream()
                .map(dealRegister -> dealRegister.getDealBoard().getD_boardId())
                .collect(Collectors.toList());

        for(DealBoardReport element : dbReports){
            // 신고 한 내역
            Report report = reportRepository.findBydBoardReportId(element.getDb_reportId());
            DealRegister dealRegister = dealRegisterRepository.findByDealBoard(element.getDealBoard()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
            ReportActivityDto reportDto = ReportActivityDto.builder()
                    .reportId(report.getReportId())
                    .reportedName(dealRegister.getUser().getName()) // User의 Name 보냄 (Nickname x)
                    .reportedId(dealRegister.getUser().getUserId()) // User의 UserId 보냄 ( UID, Token 등 수정 필요 )
                    .reportReason(element.getContent())
                    .boardId(element.getDealBoard().getD_boardId())
                    .commentId(0L)
                    .build();
            reports.add(reportDto);

            // 신고 받은 내역
            if(dBoardIds.contains(element.getDealBoard().getD_boardId())) {
                ReceivedReportDto receivedReportDto = ReceivedReportDto.builder()
                        .reportId(report.getReportId())
                        .reporterName(element.getUser().getName()) // User의 Name 보냄 (Nickname x)
                        .reporterId(element.getUser().getUserId()) // User의 UserId 보냄 ( UID, Token 등 수정 필요 )
                        .reportReason(element.getContent())
                        .boardId(element.getDealBoard().getD_boardId())
                        .commentId(0L)
                        .build();
                receivedReports.add(receivedReportDto);
            }
        }

        // 3.3 자유 게시글 댓글 신고
        List<FreeCommentReport> fcReports = freeCommentReportRepository.findAllByUser(user);

        List<Long> fCommentIds = freeBoardCommentRepository.findAllByUser(user) // 해당 유저가 작성한 모든 게시글의 id 값을 가지고 있는 리스트
                .stream()
                .map(freeBoardComment -> freeBoardComment.getF_commentId())
                .collect(Collectors.toList());

        for(FreeCommentReport element : fcReports){
            // 신고 한 내역
            Report report = reportRepository.findByfCommentReportId(element.getFc_reportId());
            ReportActivityDto reportDto = ReportActivityDto.builder()
                    .reportId(report.getReportId())
                    .reportedName(element.getFreeBoardComment().getUser().getName()) // User의 Name 보냄 (Nickname x)
                    .reportedId(element.getFreeBoardComment().getUser().getUserId()) // User의 UserId 보냄 ( UID, Token 등 수정 필요 )
                    .reportReason(element.getContent())
                    .boardId(element.getFreeBoardComment().getFreeBoard().getF_boardId())
                    .commentId(element.getFreeBoardComment().getF_commentId())
                    .build();
            reports.add(reportDto);

            // 신고 받은 내역
            if(fCommentIds.contains(element.getFreeBoardComment().getF_commentId())) {
                ReceivedReportDto receivedReportDto = ReceivedReportDto.builder()
                        .reportId(report.getReportId())
                        .reporterName(element.getUser().getName()) // User의 Name 보냄 (Nickname x)
                        .reporterId(element.getUser().getUserId()) // User의 UserId 보냄 ( UID, Token 등 수정 필요 )
                        .reportReason(element.getContent())
                        .boardId(element.getFreeBoardComment().getFreeBoard().getF_boardId())
                        .commentId(element.getFreeBoardComment().getF_commentId())
                        .build();
                receivedReports.add(receivedReportDto);
            }
        }

        // 3.4 거래 게시글 댓글 신고
        List<DealCommentReport> dcReports = dealCommentReportRepository.findAllByUser(user);

        List<Long> dCommentIds = dealBoardCommentRepository.findAllByUser(user) // 해당 유저가 작성한 모든 게시글의 id 값을 가지고 있는 리스트
                .stream()
                .map(dealBoardComment -> dealBoardComment.getD_commentId())
                .collect(Collectors.toList());

        for(DealCommentReport element : dcReports){
            // 신고 한 내역
            Report report = reportRepository.findBydCommentReportId(element.getDc_reportId());
            ReportActivityDto reportDto = ReportActivityDto.builder()
                    .reportId(report.getReportId())
                    .reportedName(element.getDealBoardComment().getUser().getName()) // User의 Name 보냄 (Nickname x)
                    .reportedId(element.getDealBoardComment().getUser().getUserId()) // User의 UserId 보냄 ( UID, Token 등 수정 필요 )
                    .reportReason(element.getContent())
                    .boardId(element.getDealBoardComment().getDealBoard().getD_boardId())
                    .commentId(element.getDealBoardComment().getD_commentId())
                    .build();
            reports.add(reportDto);

            // 신고 받은 내역
            if(dCommentIds.contains(element.getDealBoardComment().getD_commentId())) {
                ReceivedReportDto receivedReportDto = ReceivedReportDto.builder()
                        .reportId(report.getReportId())
                        .reporterName(element.getUser().getName()) // User의 Name 보냄 (Nickname x)
                        .reporterId(element.getUser().getUserId()) // User의 UserId 보냄 ( UID, Token 등 수정 필요 )
                        .reportReason(element.getContent())
                        .boardId(element.getDealBoardComment().getDealBoard().getD_boardId())
                        .commentId(element.getDealBoardComment().getD_commentId())
                        .build();
                receivedReports.add(receivedReportDto);
            }
        }

        return ActivityListDto.builder()
                .boardActivityDtos(boardActivityDtos)
                .commentActivityDtos(commentActivityDtos)
                .reportActivityDtos(reports)
                .receivedReportDtos(receivedReports)
                .build();

    }

    public List<MainResponse> showAllUserBySearch(Long userId, String query, int pageIndex, int pageSize) {

        List<User> users = new ArrayList<>();

        if(!ObjectUtils.isEmpty(userId) && ObjectUtils.isEmpty(query)){
            users.add(userRepository.findById(userId).get());
        }
        else if(ObjectUtils.isEmpty(userId) && !ObjectUtils.isEmpty(query)){
            log.info("@@@@@@@@@@@@@@@@@@@@@@@@@");
            List<User> names = userRepository.findByNameContains(query);
            List<User> nicknames = userRepository.findByNicknameContains(query);
            List<User> emails = userRepository.findByEmailContains(query);
            List<User> result = new ArrayList<>(names);
            result.removeAll(nicknames);
            result.addAll(nicknames);
            result.removeAll(emails);
            result.addAll(emails);
            if(pageIndex*pageSize+pageSize >= result.size()){
                users = result.subList(pageIndex*pageSize,result.size());
            }
            else{
                users = result.subList(pageIndex*pageSize,pageIndex*pageSize+pageSize);
            }
        }
        else if(!ObjectUtils.isEmpty(userId) && !ObjectUtils.isEmpty(query)){
            User user = userRepository.findById(userId).get();
            if(user.getName().contains(query) || user.getNickname().contains(query) || user.getEmail().contains(query)){
                users.add(user);
            }
        }
        else{
            log.info("!!!!!!!!!!!!!!!!!!!!!!");
            users = getAllUserList(pageIndex, pageSize);
        }

        return users.stream()
                .map(user -> MainResponse.builder()
                        .userId(user.getUserId())
                        .userName(user.getName())
                        .nickName(user.getNickname())
                        .sex(user.getSex())
                        .birth(user.getBirthday())
                        .region(user.getLocation())
                        .timepay(user.getUserProfile().getTimepay()) // 확인 필요
                        .build())
                .sorted(Comparator.comparing(MainResponse::getUserId))
                .collect(Collectors.toList());
    }
}
