package com.capstone.timepay.service.board.service;

import com.capstone.timepay.controller.board.request.ReportRequestDTO;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardRepository;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardCommentRepository;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReport;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReportRepository;
import com.capstone.timepay.domain.dealCommentReport.DealCommentReport;
import com.capstone.timepay.domain.dealCommentReport.DealCommentReportRepository;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.freeBoard.FreeBoardRepository;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardCommentRepository;
import com.capstone.timepay.domain.freeBoardReport.FreeBoardReport;
import com.capstone.timepay.domain.freeBoardReport.FreeBoardReportRepository;
import com.capstone.timepay.domain.freeCommentReport.FreeCommentReport;
import com.capstone.timepay.domain.freeCommentReport.FreeCommentReportRepository;
import com.capstone.timepay.domain.report.Report;
import com.capstone.timepay.domain.report.ReportRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final UserRepository userRepository;
    private final DealBoardRepository dealBoardRepository;
    private final FreeBoardRepository freeBoardRepository;
    private final DealBoardCommentRepository dealBoardCommentRepository;
    private final FreeBoardCommentRepository freeBoardCommentRepository;
    private final DealBoardReportRepository dealBoardReportRepository;
    private final FreeBoardReportRepository freeBoardReportRepository;
    private final DealCommentReportRepository dealCommentReportRepository;
    private final FreeCommentReportRepository freeCommentReportRepository;
    private final ReportRepository reportRepository;


    public Map<String, Object> reportBoard(Authentication auth, Long boardId, ReportRequestDTO reportRequestDTO, String type){
        Map<String, Object> result = new HashMap<>();
        User reporter = userRepository.findByEmail(auth.getName()).orElse(null);
        try {
            if (type.equals("거래신고")) {
                DealBoard dealBoard = dealBoardRepository.findById(boardId).orElse(null);
                // DealBoardReport dealBoardReport = new DealBoardReport(reporter, reportRequestDTO.getReportBody(), dealBoard);
                // Report reportData = new Report(null, dealBoardReport, null, null);
                // dealBoardReportRepository.save(dealBoardReport);
                // reportRepository.save(reportData);

                DealBoardReport dealBoardReport = DealBoardReport.builder()
                        .user(reporter)
                        .content(reportRequestDTO.getReportBody())
                        .process("처리중")
                        .dealBoard(dealBoard)
                        .build();
                dealBoardReportRepository.save(dealBoardReport);

                Report reportData = Report.builder()
                        .freeBoardReport(null)
                        .dealBoardReport(dealBoardReport)
                        .freeCommentReport(null)
                        .dealCommentReport(null)
                        .build();
                reportRepository.save(reportData);

                result.put("success", true);
                return result;

            } else if (type.equals("일반신고")) {
                FreeBoard freeBoard = freeBoardRepository.findById(boardId).orElse(null);
                FreeBoardReport freeBoardReport = FreeBoardReport.builder()
                        .user(reporter)
                        .content(reportRequestDTO.getReportBody())
                        .process("처리중")
                        .freeBoard(freeBoard)
                        .build();
                freeBoardReportRepository.save(freeBoardReport);

                Report reportData = Report.builder()
                        .freeBoardReport(freeBoardReport)
                        .dealBoardReport(null)
                        .freeCommentReport(null)
                        .dealCommentReport(null)
                        .build();
                reportRepository.save(reportData);

                result.put("success", true);
                return result;
            }
        } catch(NullPointerException e){
            result.put("success", false);
            result.put("message", "존재하지 않는 유저 또는 게시글입니다.");
            return result;
        }

        result.put("success", false);
        result.put("message", "잘못된 유형의 게시판입니다.");
        return result;
    }

    public Map<String, Object> reportComment(Authentication auth, Long boardId, Long commentId, ReportRequestDTO requestDTO, String type){
        Map<String, Object> result = new HashMap<>();
        User reporter = userRepository.findByEmail(auth.getName()).orElse(null);
        try {

            if (type.equals("거래댓글신고")) {
                DealBoardComment dealBoardComment = dealBoardCommentRepository.findById(commentId)
                        .orElse(null);

                DealCommentReport dealCommentReport = DealCommentReport.builder()
                        .user(reporter)
                        .content(requestDTO.getReportBody())
                        .process("처리중")
                        .dealBoardComment(dealBoardComment)
                        .build();
                dealCommentReportRepository.save(dealCommentReport);

                Report reportData = Report.builder()
                        .freeBoardReport(null)
                        .dealBoardReport(null)
                        .freeCommentReport(null)
                        .dealCommentReport(dealCommentReport)
                        .build();
                reportRepository.save(reportData);

                result.put("success", true);
                return result;

            } else if (type.equals("일반댓글신고")) {
                FreeBoardComment freeBoardComment = freeBoardCommentRepository.findById(commentId)
                        .orElse(null);

                FreeCommentReport freeCommentReport = FreeCommentReport.builder()
                        .user(reporter)
                        .content(requestDTO.getReportBody())
                        .process("처리중")
                        .freeBoardComment(freeBoardComment)
                        .build();
                freeCommentReportRepository.save(freeCommentReport);

                Report reportData = Report.builder()
                        .freeBoardReport(null)
                        .dealBoardReport(null)
                        .freeCommentReport(freeCommentReport)
                        .dealCommentReport(null)
                        .build();
                reportRepository.save(reportData);

                result.put("success", true);
                return result;
            }
        } catch(NullPointerException e){
            result.put("success", false);
            result.put("message", "존재하지 않는 유저 또는 게시글입니다.");
            return result;
        }

        result.put("success", false);
        result.put("message", "잘못된 유형의 게시판입니다.");
        return result;
    }
}
