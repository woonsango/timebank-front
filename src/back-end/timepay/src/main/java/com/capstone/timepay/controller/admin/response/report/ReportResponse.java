package com.capstone.timepay.controller.admin.response.report;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {

    private Long reportId;  // 전체 신고 테이블 pk값
    private Long reporterId;    // 신고자 회원번호
    private String reporterName;    //신고자 이름
    private String type;    //
    private String reason;
    private Long targetId;  //신고 대상 글번호
    private LocalDateTime reportedAt;
    private Long targetReportId;    // 신고 대상자 회원번호
    private String process;  // 처리 여부에서 처리 상태로 바꿈

}
