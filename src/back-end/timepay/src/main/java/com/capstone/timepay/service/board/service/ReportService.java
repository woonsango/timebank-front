package com.capstone.timepay.service.board.service;

import com.capstone.timepay.controller.board.request.ReportRequestDTO;
import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardRepository;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReport;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReportRepository;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.freeBoard.FreeBoardRepository;
import com.capstone.timepay.domain.freeBoardReport.FreeBoardReport;
import com.capstone.timepay.domain.report.Report;
import com.capstone.timepay.domain.report.ReportRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final UserRepository userRepository;
    private final DealBoardRepository dealBoardRepository;
    private final FreeBoardRepository freeBoardRepository;

    private final DealBoardReportRepository dealBoardReportRepository;
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

                Report reportData = Report.builder()
                        .freeBoardReport(null)
                        .dealBoardReport(dealBoardReport)
                        .freeCommentReport(null)
                        .dealCommentReport(null)
                        .build();

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

                Report reportData = Report.builder()
                        .freeBoardReport(freeBoardReport)
                        .dealBoardReport(null)
                        .freeCommentReport(null)
                        .dealCommentReport(null)
                        .build();

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

    public boolean reportComment(Authentication auth, Long boardId, Long commentId, ReportRequestDTO requestDTO, String type){
        User user = userRepository.findByEmail(auth.getName()).orElse(null);

        if(type.equals("거래댓글신고")){

            return true;

        } else if(type.equals("일반댓글신고")) {

            return true;
        }

        return false;
    }
}
