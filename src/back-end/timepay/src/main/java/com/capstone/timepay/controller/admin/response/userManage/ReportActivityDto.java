package com.capstone.timepay.controller.admin.response.userManage;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportActivityDto {

    private Long reportId;
    private String reportedName;
    private Long reportedId;
    private String reportReason;
    private Long boardId;
    private Long commentId;


}
