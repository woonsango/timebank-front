package com.capstone.timepay.service.admin;

import com.capstone.timepay.controller.admin.response.comment.CommentResponse;
import com.capstone.timepay.controller.admin.response.report.ReportResponse;
import com.capstone.timepay.domain.comment.Comment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReport;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReportRepository;
import com.capstone.timepay.domain.dealCommentReport.DealCommentReport;
import com.capstone.timepay.domain.dealCommentReport.DealCommentReportRepository;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.dealRegister.DealRegisterRepository;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
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
import com.capstone.timepay.service.admin.dto.CommentSearchDto;
import com.capstone.timepay.service.admin.dto.PenaltyUserDto;
import com.capstone.timepay.service.admin.dto.ReportSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportManageService {

    private final ReportRepository reportRepository;
    private final FreeBoardReportRepository freeBoardReportRepository;
    private final FreeCommentReportRepository freeCommentReportRepository;
    private final DealBoardReportRepository dealBoardReportRepository;
    private final DealCommentReportRepository dealCommentReportRepository;
    private final FreeRegisterRepository freeRegisterRepository;
    private final DealRegisterRepository dealRegisterRepository;
    private final UserRepository userRepository;

    public Page<ReportResponse> showAllReports(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("reportId"));
        Page<Report> reports = reportRepository.findAll(pageable);

        return convertResponsePages(reports);
    }

    public Page<ReportResponse> convertResponsePages(Page<Report> reports){
        Page<ReportResponse> pageResponses = reports.map(new Function<Report, ReportResponse>() {
            @Override
            public ReportResponse apply(Report report) {

                // 1. 자유 게시판 신고
                if(!Objects.isNull(report.getFreeBoardReport())){
                    FreeRegister fr = freeRegisterRepository.findByFreeBoard(report.getFreeBoardReport().getFreeBoard()).orElseThrow(()->new IllegalArgumentException("1존재하지 않는 게시글입니다."));
                    return ReportResponse.builder()
                            .reportId(report.getReportId())
                            .reporterId(report.getFreeBoardReport().getUser().getUserId())
                            .reporterName(report.getFreeBoardReport().getUser().getName())
                            .type("자유게시글")
                            .reason(report.getFreeBoardReport().getContent())
                            .targetId(report.getFreeBoardReport().getFreeBoard().getF_boardId())
                            .reportedAt(report.getFreeBoardReport().getCreatedAt())
                            .targetReportId(fr.getUser().getUserId())
                            .process(report.getFreeBoardReport().getProcess())
                            .build();
                }
                // 2. 자유 게시판 댓글 신고
                else if(!Objects.isNull(report.getFreeCommentReport())){
                    FreeCommentReport fcr = freeCommentReportRepository.findById(report.getFreeCommentReport().getFc_reportId()).orElseThrow(()->new IllegalArgumentException("2존재하지 않는 신고입니다."));
                    return ReportResponse.builder()
                            .reportId(report.getReportId())
                            .reporterId(report.getFreeCommentReport().getUser().getUserId())
                            .reporterName(report.getFreeCommentReport().getUser().getName())
                            .type("자유게시글댓글")
                            .reason(report.getFreeCommentReport().getContent())
                            .targetId(report.getFreeCommentReport().getFreeBoardComment().getF_commentId())
                            .reportedAt(report.getFreeCommentReport().getCreatedAt())
                            .targetReportId(fcr.getFreeBoardComment().getUser().getUserId())
                            .process(report.getFreeCommentReport().getProcess())
                            .build();
                }
                // 3. 거래 게시판 신고
                else if(!Objects.isNull(report.getDealBoardReport())){
                    DealRegister dr = dealRegisterRepository.findByDealBoard(report.getDealBoardReport().getDealBoard()).orElseThrow(()->new IllegalArgumentException("3존재하지 않는 게시글입니다."));
                    return ReportResponse.builder()
                            .reportId(report.getReportId())
                            .reporterId(report.getDealBoardReport().getUser().getUserId())
                            .reporterName(report.getDealBoardReport().getUser().getName())
                            .type("거래게시글")
                            .reason(report.getDealBoardReport().getContent())
                            .targetId(report.getDealBoardReport().getDealBoard().getD_boardId())
                            .reportedAt(report.getDealBoardReport().getCreatedAt())
                            .targetReportId(dr.getUser().getUserId())
                            .process(report.getDealBoardReport().getProcess())
                            .build();
                }
                // 4. 거래 게시판 댓글 신고
                else{
                    DealCommentReport dcr = dealCommentReportRepository.findById(report.getDealCommentReport().getDc_reportId()).orElseThrow(()->new IllegalArgumentException("4존재하지 않는 신고입니다."));
                    return ReportResponse.builder()
                            .reportId(report.getReportId())
                            .reporterId(report.getDealCommentReport().getUser().getUserId())
                            .reporterName(report.getDealCommentReport().getUser().getName())
                            .type("거래게시글댓글")
                            .reason(report.getDealCommentReport().getContent())
                            .targetId(report.getDealCommentReport().getDealBoardComment().getD_commentId())
                            .reportedAt(report.getDealCommentReport().getCreatedAt())
                            .targetReportId(dcr.getDealBoardComment().getUser().getUserId())
                            .process(report.getDealCommentReport().getProcess())
                            .build();
                }

            }
        });

        return pageResponses;
    }

    public void penaltyUsers(PenaltyUserDto usersDto) {
        for(Long id : usersDto.getUserIds()){
            User user = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 유저입니다."));
            user.registerBlacklist();
            userRepository.save(user);
        }
    }

    public Page<ReportResponse> showReportsBySearch(String query, String value, LocalDateTime startDate, LocalDateTime endDate) {

        if(Objects.isNull(startDate) || Objects.isNull(endDate)){
            if(Objects.isNull(query) || Objects.isNull(value)) throw new IllegalArgumentException("잘못된 파라미터 요청입니다.");
        }

        if(query.equals("reportId")){

            Report report = reportRepository.findByReportId(Long.parseLong(value)).orElse(null);

            if(Objects.isNull(report)) return new PageImpl<>(new ArrayList<>());

            List<Report> reports = new ArrayList<>();
            reports.add(report);

            return convertResponsePages(new PageImpl<>(reports));

        }
        else if(query.equals("name")){

            List<User> users = userRepository.findAllByNameContains(value);

            if(ObjectUtils.isEmpty(users)) return new PageImpl<>(new ArrayList<>());

            List<ReportResponse> fcrs = new ArrayList<>();
            List<ReportResponse> fbrs = new ArrayList<>();
            List<ReportResponse> dcrs = new ArrayList<>();
            List<ReportResponse> dbrs = new ArrayList<>();
            for(User user : users){
                fcrs.addAll(convertFCRToResponse(freeCommentReportRepository.findAllByUser(user)));
            }
            for(User user : users){
                fbrs.addAll(convertFBRToResponse(freeBoardReportRepository.findAllByUser(user)));
            }
            for(User user : users){
                dcrs.addAll(convertDCRToResponse(dealCommentReportRepository.findAllByUser(user)));
            }
            for(User user : users){
                dbrs.addAll(convertDBRToResponse(dealBoardReportRepository.findAllByUser(user)));
            }

            List<ReportResponse> reportResponses = new ArrayList<>();
            reportResponses.addAll(fbrs);
            reportResponses.addAll(fcrs);
            reportResponses.addAll(dbrs);
            reportResponses.addAll(dcrs);

            return new PageImpl<>(reportResponses);
        }
        else if(query.equals("content")){

            List<ReportResponse> fcrs = convertFCRToResponse(freeCommentReportRepository.findByContentContains(value));
            List<ReportResponse> fbrs = convertFBRToResponse(freeBoardReportRepository.findByContentContains(value));
            List<ReportResponse> dcrs = convertDCRToResponse(dealCommentReportRepository.findByContentContains(value));
            List<ReportResponse> dbrs = convertDBRToResponse(dealBoardReportRepository.findByContentContains(value));

            List<ReportResponse> reportResponses = new ArrayList<>();
            reportResponses.addAll(fbrs);
            reportResponses.addAll(fcrs);
            reportResponses.addAll(dbrs);
            reportResponses.addAll(dcrs);

            return new PageImpl<>(reportResponses);
        }
        else if(!ObjectUtils.isEmpty(startDate) && !ObjectUtils.isEmpty(endDate)){

            List<ReportResponse> fcrs =
                    convertFCRToResponse(freeCommentReportRepository.findByCreatedAtLessThanEqualAndCreatedAtGreaterThanEqual(endDate, startDate));
            List<ReportResponse> fbrs =
                    convertFBRToResponse(freeBoardReportRepository.findByCreatedAtLessThanEqualAndCreatedAtGreaterThanEqual(endDate, startDate));
            List<ReportResponse> dcrs =
                    convertDCRToResponse(dealCommentReportRepository.findByCreatedAtLessThanEqualAndCreatedAtGreaterThanEqual(endDate, startDate));
            List<ReportResponse> dbrs =
                    convertDBRToResponse(dealBoardReportRepository.findByCreatedAtLessThanEqualAndCreatedAtGreaterThanEqual(endDate, startDate));

            List<ReportResponse> reportResponses = new ArrayList<>();
            reportResponses.addAll(fbrs);
            reportResponses.addAll(fcrs);
            reportResponses.addAll(dbrs);
            reportResponses.addAll(dcrs);

            return new PageImpl<>(reportResponses);
        }
        else throw new IllegalArgumentException("잘못된 파라미터 요청입니다.");

    }
    public List<ReportResponse> convertFCRToResponse(List<FreeCommentReport> report){
        return report
                .stream()
                .map(freeCommentReport ->
                        ReportResponse.builder()
                                .reportId(reportRepository.findByFreeCommentReport(freeCommentReport).getReportId())
                                .reporterId(freeCommentReport.getUser().getUserId())
                                .reporterName(freeCommentReport.getUser().getName())
                                .type("자유게시글댓글")
                                .reason(freeCommentReport.getContent())
                                .targetId(freeCommentReport.getFreeBoardComment().getF_commentId())
                                .reportedAt(freeCommentReport.getCreatedAt())
                                .targetReportId(freeCommentReport.getFreeBoardComment().getUser().getUserId())
                                .process(freeCommentReport.getProcess())
                                .build())
                .collect(Collectors.toList());
    }
    public List<ReportResponse> convertDCRToResponse(List<DealCommentReport> report){
        return report
                .stream()
                .map(dealCommentReport ->
                        ReportResponse.builder()
                                .reportId(reportRepository.findByDealCommentReport(dealCommentReport).getReportId())
                                .reporterId(dealCommentReport.getUser().getUserId())
                                .reporterName(dealCommentReport.getUser().getName())
                                .type("거래게시글댓글")
                                .reason(dealCommentReport.getContent())
                                .targetId(dealCommentReport.getDealBoardComment().getD_commentId())
                                .reportedAt(dealCommentReport.getCreatedAt())
                                .targetReportId(dealCommentReport.getDealBoardComment().getUser().getUserId())
                                .process(dealCommentReport.getProcess())
                                .build())
                .collect(Collectors.toList());
    }
    public List<ReportResponse> convertFBRToResponse(List<FreeBoardReport> report){
        return report
                .stream()
                .map(freeBoardReport ->
                        ReportResponse.builder()
                                .reportId(reportRepository.findByFreeBoardReport(freeBoardReport).getReportId())
                                .reporterId(freeBoardReport.getUser().getUserId())
                                .reporterName(freeBoardReport.getUser().getName())
                                .type("자유게시글")
                                .reason(freeBoardReport.getContent())
                                .targetId(freeBoardReport.getFreeBoard().getF_boardId())
                                .reportedAt(freeBoardReport.getCreatedAt())
                                .targetReportId(freeRegisterRepository.findByFreeBoard(freeBoardReport.getFreeBoard()).get().getUser().getUserId())
                                .process(freeBoardReport.getProcess())
                                .build())
                .collect(Collectors.toList());
    }
    public List<ReportResponse> convertDBRToResponse(List<DealBoardReport> report){
        return report
                .stream()
                .map(dealBoardReport ->
                        ReportResponse.builder()
                                .reportId(reportRepository.findByDealBoardReport(dealBoardReport).getReportId())
                                .reporterId(dealBoardReport.getUser().getUserId())
                                .reporterName(dealBoardReport.getUser().getName())
                                .type("거래게시글")
                                .reason(dealBoardReport.getContent())
                                .targetId(dealBoardReport.getDealBoard().getD_boardId())
                                .reportedAt(dealBoardReport.getCreatedAt())
                                .targetReportId(dealRegisterRepository.findByDealBoard(dealBoardReport.getDealBoard()).get().getUser().getUserId())
                                .process(dealBoardReport.getProcess())
                                .build())
                .collect(Collectors.toList());
    }

}
