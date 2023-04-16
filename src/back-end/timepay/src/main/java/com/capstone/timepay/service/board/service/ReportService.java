package com.capstone.timepay.service.board.service;

import com.capstone.timepay.controller.board.request.ReportRequestDTO;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardRepository;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReport;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.freeBoard.FreeBoardRepository;
import com.capstone.timepay.domain.freeBoardReport.FreeBoardReport;
import com.capstone.timepay.domain.report.Report;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final UserRepository userRepository;
    private final DealBoardRepository dealBoardRepository;
    private final FreeBoardRepository freeBoardRepository;

    public boolean reportBoard(Authentication auth,Long boardId, ReportRequestDTO reportRequestDTO, String type){
        User user = userRepository.findByEmail(auth.getName()).orElse(null);
        try {
            if (type.equals("거래신고")) {
                DealBoard dealBoard = dealBoardRepository.findById(boardId).orElse(null);
                DealBoardReport dealBoardReport = new DealBoardReport(user, reportRequestDTO.getReportBody(), dealBoard);
                Report reportData = new Report(null, dealBoardReport, null, null);
                return true;

            } else if (type.equals("일반신고")) {
                FreeBoard freeBoard = freeBoardRepository.findById(boardId).orElse(null);
                FreeBoardReport freeBoardReport = new FreeBoardReport(user, reportRequestDTO.getReportBody(), freeBoard);
                Report reportData = new Report(freeBoardReport, null, null, null);
                return true;
            }
        } catch(NullPointerException e){
            return false;
        }
        return false;
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
