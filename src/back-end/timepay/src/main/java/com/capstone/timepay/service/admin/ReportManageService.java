package com.capstone.timepay.service.admin;

import com.capstone.timepay.controller.admin.response.comment.CommentResponse;
import com.capstone.timepay.controller.admin.response.report.ReportResponse;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
    public List<ReportResponse> showAllReports() {

        List<Report> fBoardReports = reportRepository.findAll().stream().filter(report -> report.getFBoardReportId()!=0).collect(Collectors.toUnmodifiableList());
        List<Report> fCommentReports = reportRepository.findAll().stream().filter(report -> report.getFCommentReportId()!=0).collect(Collectors.toUnmodifiableList());
        List<Report> dBoardReports = reportRepository.findAll().stream().filter(report -> report.getDBoardReportId()!=0).collect(Collectors.toUnmodifiableList());
        List<Report> dCommentReports = reportRepository.findAll().stream().filter(report -> report.getDCommentReportId()!=0).collect(Collectors.toUnmodifiableList());

        List<ReportResponse> responses = new ArrayList<>();

        for(Report report : fBoardReports){
            FreeBoardReport fbr = freeBoardReportRepository.findById(report.getFBoardReportId()).orElseThrow(()->new IllegalArgumentException("1존재하지 않는 신고입니다."));
            FreeRegister fr = freeRegisterRepository.findByFreeBoard(fbr.getFreeBoard()).orElseThrow(()->new IllegalArgumentException("1존재하지 않는 게시글입니다."));

            ReportResponse element = ReportResponse.builder()
                                                        .reportId(report.getReportId())
                                                        .reporterId(fbr.getUser().getUserId())
                                                        .reporterName(fbr.getUser().getName())
                                                        .type("자유게시글")
                                                        .reason(fbr.getContent())
                                                        .targetId(fbr.getFreeBoard().getF_boardId())
                                                        .reportedAt(fbr.getCreatedAt())
                                                        .targetReportId(fr.getUser().getUserId())
                                                        .process(fbr.getProcess())
                                                    .build();
            responses.add(element);
        }

        for(Report report : fCommentReports){
            FreeCommentReport fcr = freeCommentReportRepository.findById(report.getFCommentReportId()).orElseThrow(()->new IllegalArgumentException("2존재하지 않는 신고입니다."));

            ReportResponse element = ReportResponse.builder()
                    .reportId(report.getReportId())
                    .reporterId(fcr.getUser().getUserId())
                    .reporterName(fcr.getUser().getName())
                    .type("자유게시글 댓글")
                    .reason(fcr.getContent())
                    .targetId(fcr.getFreeBoardComment().getF_commentId())
                    .reportedAt(fcr.getCreatedAt())
                    .targetReportId(fcr.getFreeBoardComment().getUser().getUserId())
                    .process(fcr.getProcess())
                    .build();

            responses.add(element);
        }
        for(Report report : dBoardReports){
            DealBoardReport dbr = dealBoardReportRepository.findById(report.getDBoardReportId()).orElseThrow(()->new IllegalArgumentException("3존재하지 않는 신고입니다."));
            DealRegister dr = dealRegisterRepository.findByDealBoard(dbr.getDealBoard()).orElseThrow(()->new IllegalArgumentException("3존재하지 않는 게시글입니다."));

            ReportResponse element = ReportResponse.builder()
                    .reportId(report.getReportId())
                    .reporterId(dbr.getUser().getUserId())
                    .reporterName(dbr.getUser().getName())
                    .type("거래게시글")
                    .reason(dbr.getContent())
                    .targetId(dbr.getDealBoard().getD_boardId())
                    .reportedAt(dbr.getCreatedAt())
                    .targetReportId(dr.getUser().getUserId())
                    .process(dbr.getProcess())
                    .build();
            responses.add(element);
        }
        for(Report report : dCommentReports){
            DealCommentReport dcr = dealCommentReportRepository.findById(report.getDCommentReportId()).orElseThrow(()->new IllegalArgumentException("4존재하지 않는 신고입니다."));

            ReportResponse element = ReportResponse.builder()
                    .reportId(report.getReportId())
                    .reporterId(dcr.getUser().getUserId())
                    .reporterName(dcr.getUser().getName())
                    .type("거래게시글 댓글")
                    .reason(dcr.getContent())
                    .targetId(dcr.getDealBoardComment().getD_commentId())
                    .reportedAt(dcr.getCreatedAt())
                    .targetReportId(dcr.getDealBoardComment().getUser().getUserId())
                    .process(dcr.getProcess())
                    .build();
            responses.add(element);
        }

        return responses.stream().sorted(Comparator.comparing(ReportResponse::getReportId)).collect(Collectors.toUnmodifiableList());
    }
}
