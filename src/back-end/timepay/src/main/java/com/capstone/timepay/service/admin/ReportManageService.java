package com.capstone.timepay.service.admin;

import com.capstone.timepay.controller.admin.response.comment.CommentResponse;
import com.capstone.timepay.controller.admin.response.report.ReportResponse;
import com.capstone.timepay.domain.comment.Comment;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReport;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReportRepository;
import com.capstone.timepay.domain.dealCommentReport.DealCommentReport;
import com.capstone.timepay.domain.dealCommentReport.DealCommentReportRepository;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.dealRegister.DealRegisterRepository;
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
import com.capstone.timepay.service.admin.dto.PenaltyUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
                            .targetReportId(fcr.getUser().getUserId())
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
                            .targetReportId(dcr.getUser().getUserId())
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
}
