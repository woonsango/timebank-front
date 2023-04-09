package com.capstone.timepay.controller.admin.response.userManage;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityListDto {

    private List<BoardActivityDto> boardActivityDtos;
    private List<CommentActivityDto> commentActivityDtos;
    private List<ReceivedReportDto> receivedReportDtos;
    private List<ReportActivityDto> reportActivityDtos;

}
