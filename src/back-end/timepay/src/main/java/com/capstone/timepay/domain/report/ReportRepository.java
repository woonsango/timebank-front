package com.capstone.timepay.domain.report;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {
    Report findByFBoardReportId(Long fb_reportId);

    Report findByDBoardReportId(Long db_reportId);

    Report findByFCommentReportId(Long fc_reportId);

    Report findByDCommentReportId(Long dc_reportId);
}
