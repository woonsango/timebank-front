package com.capstone.timepay.domain.report;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReport;
import com.capstone.timepay.domain.dealCommentReport.DealCommentReport;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import com.capstone.timepay.domain.freeBoardReport.FreeBoardReport;
import com.capstone.timepay.domain.freeCommentReport.FreeCommentReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @OneToOne
    @JoinColumn(name = "fb_reportId")
    private FreeBoardReport freeBoardReport;

    @OneToOne
    @JoinColumn(name = "db_reportId")
    private DealBoardReport dealBoardReport;

    @OneToOne
    @JoinColumn(name = "fc_reportId")
    private FreeCommentReport freeCommentReport;

    @OneToOne
    @JoinColumn(name = "dc_reportId")
    private DealCommentReport dealCommentReport;

    public Report(FreeBoardReport freeBoardReport, DealBoardReport dealBoardReport, FreeCommentReport freeCommentReport, DealCommentReport dealCommentReport){
        this.freeBoardReport = freeBoardReport;
        this.dealBoardReport = dealBoardReport;
        this.freeCommentReport = freeCommentReport;
        this.dealCommentReport = dealCommentReport;
    }

}
