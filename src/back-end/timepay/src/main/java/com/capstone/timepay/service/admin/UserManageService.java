package com.capstone.timepay.service.admin;

import com.capstone.timepay.controller.admin.response.userManage.*;
import com.capstone.timepay.domain.board.BoardRepository;
import com.capstone.timepay.domain.comment.CommentRepository;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardCommentRepository;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReport;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReportRepository;
import com.capstone.timepay.domain.dealCommentReport.DealCommentReport;
import com.capstone.timepay.domain.dealCommentReport.DealCommentReportRepository;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.dealRegister.DealRegisterRepository;
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
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
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

    public Page<MainResponse> convertResponsePages(Page<User> pages){
        Page<MainResponse> pageResponses = pages.map(new Function<User, MainResponse>() {
            @Override
            public MainResponse apply(User user) {
                return MainResponse.builder()
                        .userId(user.getUserId())
                        .userName(user.getName())
                        .nickName(user.getNickname())
                        .sex(user.getSex())
                        .birth(user.getBirthday())
                        .region(user.getLocation())
                        .timepay(user.getUserProfile().getTimepay()) // 확인 필요
                        .totalVolunteerTime(user.getTotalVolunteerTime())
                        .isBanned(user.isBanned())
                        .profileUrl(user.getUserProfile().getImageUrl())
                        .build();
            }
        });

        return pageResponses;
    }

    public Page<MainResponse> showAllUserList(int pageIndex, int pageSize) {

        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("userId"));
        //Page<User> pages = userRepository.findAll(pageable);
        Page<User> pages = userRepository.findAllByOrganizationIsNull(pageable);

        return convertResponsePages(pages);
    }

    public Page<MainResponse> showAllUserBySearch(Long userId, String query, String value, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        if(!ObjectUtils.isEmpty(userId)){
            //List<User> user = userRepository.findById(userId).stream().collect(Collectors.toList());
            List<User> user = userRepository.findByUserIdAndOrganizationIsNull(userId).stream().collect(Collectors.toList());
            Page<User> pages = new PageImpl<>(user);
            return convertResponsePages(pages);
        }
        else if(!ObjectUtils.isEmpty(query) && !ObjectUtils.isEmpty(value)){
            if(query.equals("name")){
                //Page<User> pages = userRepository.findAllByName(pageable, value);
                Page<User> pages = userRepository.findAllByNameAndOrganizationIsNull(pageable, value);
                return convertResponsePages(pages);
            }
            else if(query.equals("nickname")){
                //Page<User> pages = userRepository.findAllByNickname(pageable, value);
                Page<User> pages = userRepository.findAllByNicknameAndOrganizationIsNull(pageable, value);
                return convertResponsePages(pages);
            }
            else{
                //Page<User> pages = userRepository.findAllByEmail(pageable, value);
                Page<User> pages = userRepository.findAllByEmailAndOrganizationIsNull(pageable, value);
                return convertResponsePages(pages);
            }
        }
        else throw new IllegalArgumentException("잘못된 파라미터 요청입니다.");
    }

    public void updateUserInfo(UserUpdateDto userInfo) {

        User user = userRepository.findById(userInfo.getUserId()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
        user.updateNickname(userInfo.getNickname());
        user.updateBirth(userInfo.getBirth());
        user.updateName(userInfo.getName());
        user.updateLocation(userInfo.getRegion());

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

    public Page<FreeBoardActivityDto> convertFreeBoardPages(Page<FreeRegister> pages){
        Page<FreeBoardActivityDto> pageResponses = pages.map(new Function<FreeRegister, FreeBoardActivityDto>() {
            @Override
            public FreeBoardActivityDto apply(FreeRegister fr) {
                return FreeBoardActivityDto.builder()
                        .boardId(fr.getFreeBoard().getF_boardId())
                        .createdAt(fr.getFreeBoard().getCreatedAt())
                        .boardType(fr.getFreeBoard().getType())
                        .boardState(fr.getFreeBoard().getCategory())    // 일단 카테고리로 넣어놓음
                        .startTime(fr.getFreeBoard().getCreatedAt())
                        .endTime(fr.getFreeBoard().getCreatedAt())
                        .title(fr.getFreeBoard().getTitle())
                        .build();
            }
        });

        return pageResponses;   // 자유 게시판이라 필요 없는 데이터는 생성 시간으로 채움
    }
    public Page<DealBoardActivityDto> convertDealBoardPages(Page<DealRegister> pages){
        Page<DealBoardActivityDto> pageResponses = pages.map(new Function<DealRegister, DealBoardActivityDto>() {
            @Override
            public DealBoardActivityDto apply(DealRegister dr) {
                return DealBoardActivityDto.builder()
                        .boardId(dr.getDealBoard().getD_boardId())
                        .createdAt(dr.getDealBoard().getCreatedAt())
                        .boardType(dr.getDealBoard().getType())
                        .boardState(dr.getDealBoard().getBoardStatus())
                        .startTime(dr.getDealBoard().getStartTime())
                        .endTime(dr.getDealBoard().getEndTime())
                        .title(dr.getDealBoard().getTitle())
                        .isVolunteer(dr.getDealBoard().isVolunteer())
                        .build();
            }
        });

        return pageResponses;
    }

    public Page<FreeCommentActivityDto> convertFreeCommentPages(Page<FreeBoardComment> pages){
        Page<FreeCommentActivityDto> pageResponses = pages.map(new Function<FreeBoardComment, FreeCommentActivityDto>() {
            @Override
            public FreeCommentActivityDto apply(FreeBoardComment fc) {
                return FreeCommentActivityDto.builder()
                        .commentId(fc.getF_commentId())
                        .createdAt(fc.getCreatedAt())
                        .isAdopted(false)
                        .isApplied(false)
                        .content(fc.getContent())
                        .build();
            }
        });

        return pageResponses;
    }
    public Page<DealCommentActivityDto> convertDealCommentPages(Page<DealBoardComment> pages){
        Page<DealCommentActivityDto> pageResponses = pages.map(new Function<DealBoardComment, DealCommentActivityDto>() {
            @Override
            public DealCommentActivityDto apply(DealBoardComment dc) {
                return DealCommentActivityDto.builder()
                        .commentId(dc.getD_commentId())
                        .createdAt(dc.getCreatedAt())
                        .isAdopted(dc.isAdopted())
                        .isApplied(dc.isApplied())
                        .content(dc.getContent())
                        .build();
            }
        });

        return pageResponses;
    }

    public ActivityListDto getActivityList(Long userId, int pageIndex, int pageSize) {

        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createdAt").descending());

        // 1. 활동목록 - 자유 게시글 활동 목록
        Page<FreeRegister> freeRegisters = freeRegisterRepository.findAllByUser(user,pageable);
        Page<FreeBoardActivityDto> freeBoardActivityDtos = convertFreeBoardPages(freeRegisters);

        // 2. 활동목록 - 거래 게시글 활동 목록
        Page<DealRegister> dealRegisters = dealRegisterRepository.findAllByUser(user,pageable);
        Page<DealBoardActivityDto> dealBoardActivityDtos = convertDealBoardPages(dealRegisters);

        // 3. 활동목록 - 자유 게시글 댓글 활동 목록
        Page<FreeBoardComment> freeBoardComments = freeBoardCommentRepository.findAllByUser(user,pageable);
        Page<FreeCommentActivityDto> freeCommentActivityDtos = convertFreeCommentPages(freeBoardComments);

        // 4. 활동목록 - 거래 게시글 댓글 활동 목록
        Page<DealBoardComment> dealBoardComments = dealBoardCommentRepository.findAllByUser(user,pageable);
        Page<DealCommentActivityDto> dealCommentActivityDtos = convertDealCommentPages(dealBoardComments);

        // 5. 활동목록 - 신고 한 내역 + 신고 받은 내역
        List<ReportActivityDto> reports = new ArrayList<>();
        List<ReceivedReportDto> receivedReports = new ArrayList<>();

        // 5.1 자유 게시글 신고
        // 신고 한 내역
        List<FreeBoardReport> fbReports = freeBoardReportRepository.findAllByUser(user);
        for(FreeBoardReport element : fbReports){
            Report report = reportRepository.findByFreeBoardReport(element);
            FreeRegister freeRegister = freeRegisterRepository.findByFreeBoard(element.getFreeBoard()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
            ReportActivityDto reportDto = ReportActivityDto.builder()
                    .reportId(report.getReportId())
                    .reportedName(freeRegister.getUser().getName()) // User의 Name 보냄 (Nickname x)
                    .reportedId(freeRegister.getUser().getUserId()) // User의 UserId 보냄 ( UID, Token 등 수정 필요 )
                    .reportReason(element.getContent())
                    .boardId(element.getFreeBoard().getF_boardId())
                    .commentId(0L)  // 넣을게 없어서 일단 0 넣음
                    .build();
            reports.add(reportDto);
        }
        // 신고 받은 내역
        List<FreeBoardReport> freeBoardReports = freeBoardReportRepository.findAll();
        List<Long> fBoardIds = freeRegisterRepository.findAllByUser(user) // 해당 유저가 작성한 모든 게시글의 id 값을 가지고 있는 리스트
                .stream()
                .map(freeRegister -> freeRegister.getFreeBoard().getF_boardId())
                .collect(Collectors.toList());
        for(FreeBoardReport element : freeBoardReports){
            if(fBoardIds.contains(element.getFreeBoard().getF_boardId())){
                Report report = reportRepository.findByFreeBoardReport(element);
                ReceivedReportDto receivedReportDto = ReceivedReportDto.builder()
                        .reportId(report.getReportId())
                        .reporterName(element.getUser().getName()) // User의 Name 보냄 (Nickname x)
                        .reporterId(element.getUser().getUserId()) // User의 UserId 보냄 ( UID, Token 등 수정 필요 )
                        .reportReason(element.getContent())
                        .boardId(element.getFreeBoard().getF_boardId())
                        .commentId(0L) // 넣을게 없어서 일단 0 넣음
                        .build();
                receivedReports.add(receivedReportDto);
            }
        }
        // 5.2 거래 게시글 신고
        // 신고 한 내역
        List<DealBoardReport> dbReports = dealBoardReportRepository.findAllByUser(user);
        for(DealBoardReport element : dbReports){
            Report report = reportRepository.findByDealBoardReport(element);
            DealRegister dealRegister = dealRegisterRepository.findByDealBoard(element.getDealBoard()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
            ReportActivityDto reportDto = ReportActivityDto.builder()
                    .reportId(report.getReportId())
                    .reportedName(dealRegister.getUser().getName()) // User의 Name 보냄 (Nickname x)
                    .reportedId(dealRegister.getUser().getUserId()) // User의 UserId 보냄 ( UID, Token 등 수정 필요 )
                    .reportReason(element.getContent())
                    .boardId(element.getDealBoard().getD_boardId())
                    .commentId(0L)  // 넣을게 없어서 일단 0 넣음
                    .build();
            reports.add(reportDto);
        }
        // 신고 받은 내역
        List<DealBoardReport> dealBoardReports = dealBoardReportRepository.findAll();
        List<Long> dBoardIds = dealRegisterRepository.findAllByUser(user) // 해당 유저가 작성한 모든 게시글의 id 값을 가지고 있는 리스트
                .stream()
                .map(dealRegister -> dealRegister.getDealBoard().getD_boardId())
                .collect(Collectors.toList());
        for(DealBoardReport element : dealBoardReports){
            if(dBoardIds.contains(element.getDealBoard().getD_boardId())){
                Report report = reportRepository.findByDealBoardReport(element);
                ReceivedReportDto receivedReportDto = ReceivedReportDto.builder()
                        .reportId(report.getReportId())
                        .reporterName(element.getUser().getName()) // User의 Name 보냄 (Nickname x)
                        .reporterId(element.getUser().getUserId()) // User의 UserId 보냄 ( UID, Token 등 수정 필요 )
                        .reportReason(element.getContent())
                        .boardId(element.getDealBoard().getD_boardId())
                        .commentId(0L) // 넣을게 없어서 일단 0 넣음
                        .build();
                receivedReports.add(receivedReportDto);
            }
        }
        // 5.3 자유 게시글 댓글 신고
        // 신고 한 내역
        List<FreeCommentReport> fcReports = freeCommentReportRepository.findAllByUser(user);
        for(FreeCommentReport element : fcReports){
            Report report = reportRepository.findByFreeCommentReport(element);
            ReportActivityDto reportDto = ReportActivityDto.builder()
                    .reportId(report.getReportId())
                    .reportedName(element.getFreeBoardComment().getUser().getName()) // User의 Name 보냄 (Nickname x)
                    .reportedId(element.getFreeBoardComment().getUser().getUserId()) // User의 UserId 보냄 ( UID, Token 등 수정 필요 )
                    .reportReason(element.getContent())
                    .boardId(element.getFreeBoardComment().getFreeBoard().getF_boardId())
                    .commentId(element.getFreeBoardComment().getF_commentId())
                    .build();
            reports.add(reportDto);
        }
        // 신고 받은 내역
        List<FreeCommentReport> freeCommentReports = freeCommentReportRepository.findAll();
        List<Long> fCommentIds = freeBoardCommentRepository.findAllByUser(user) // 해당 유저가 작성한 모든 게시글의 id 값을 가지고 있는 리스트
                .stream()
                .map(freeBoardComment -> freeBoardComment.getF_commentId())
                .collect(Collectors.toList());
        for(FreeCommentReport element : freeCommentReports){
            if(fCommentIds.contains(element.getFreeBoardComment().getF_commentId())) {
                Report report = reportRepository.findByFreeCommentReport(element);
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
        // 5.4 거래 게시글 댓글 신고
        // 신고 한 내역
        List<DealCommentReport> dcReports = dealCommentReportRepository.findAllByUser(user);
        for(DealCommentReport element : dcReports){
            Report report = reportRepository.findByDealCommentReport(element);
            ReportActivityDto reportDto = ReportActivityDto.builder()
                    .reportId(report.getReportId())
                    .reportedName(element.getDealBoardComment().getUser().getName()) // User의 Name 보냄 (Nickname x)
                    .reportedId(element.getDealBoardComment().getUser().getUserId()) // User의 UserId 보냄 ( UID, Token 등 수정 필요 )
                    .reportReason(element.getContent())
                    .boardId(element.getDealBoardComment().getDealBoard().getD_boardId())
                    .commentId(element.getDealBoardComment().getD_commentId())
                    .build();
            reports.add(reportDto);
        }
        // 신고 받은 내역
        List<DealCommentReport> dealCommentReports = dealCommentReportRepository.findAll();
        List<Long> dCommentIds = dealBoardCommentRepository.findAllByUser(user) // 해당 유저가 작성한 모든 게시글의 id 값을 가지고 있는 리스트
                .stream()
                .map(freeBoardComment -> freeBoardComment.getD_commentId())
                .collect(Collectors.toList());
        for(DealCommentReport element : dealCommentReports){
            if(dCommentIds.contains(element.getDealBoardComment().getD_commentId())) {
                Report report = reportRepository.findByDealCommentReport(element);
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
                .freeBoardActivityDtos(freeBoardActivityDtos)
                .dealBoardActivityDtos(dealBoardActivityDtos)
                .freeCommentActivityDtos(freeCommentActivityDtos)
                .dealCommentActivityDtos(dealCommentActivityDtos)
                .reportActivityDtos(new PageImpl<>(reports))
                .receivedReportDtos(new PageImpl<>(receivedReports))
                .build();

    }


}
