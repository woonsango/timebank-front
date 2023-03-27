package com.capstone.timepay.domain.report;

import com.capstone.timepay.domain.BaseTimeEntity;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reportId;

    @Column
    private Long fBoardReportId;
    private Long dBoardReportId;
    private Long fCommentReportId;
    private Long dCommentReportId;

}
