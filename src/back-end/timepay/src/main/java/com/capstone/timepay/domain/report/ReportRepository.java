package com.capstone.timepay.domain.report;

import com.capstone.timepay.domain.dealBoardReport.DealBoardReport;
import com.capstone.timepay.domain.dealCommentReport.DealCommentReport;
import com.capstone.timepay.domain.freeBoardReport.FreeBoardReport;
import com.capstone.timepay.domain.freeCommentReport.FreeCommentReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report,Long> {

    Report findByFreeBoardReport(FreeBoardReport freeBoardReport);

    Report findByDealBoardReport(DealBoardReport dealBoardReport);

    Report findByFreeCommentReport(FreeCommentReport freeCommentReport);

    Report findByDealCommentReport(DealCommentReport dealCommentReport);

    Optional<Report> findByReportId(Long reportId);
}
