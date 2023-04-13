package com.capstone.timepay.controller.admin.response.userManage;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceivedReportDto {

    private Long reportId;
    private String reporterName;
    private Long reporterId;
    private String reportReason;
    private Long boardId;
    private Long commentId;

}
