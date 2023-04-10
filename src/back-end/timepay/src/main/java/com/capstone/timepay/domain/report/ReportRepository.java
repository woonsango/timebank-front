package com.capstone.timepay.domain.report;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {
    Report findByfBoardReportId(Long fb_reportId);

    Report findBydBoardReportId(Long db_reportId);

    Report findByfCommentReportId(Long fc_reportId);

    Report findBydCommentReportId(Long dc_reportId);
}
