package com.capstone.timepay.controller.admin.response.userManage;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityListDto {

    private Page<FreeBoardActivityDto> freeBoardActivityDtos;
    private Page<FreeCommentActivityDto> freeCommentActivityDtos;
    private Page<DealBoardActivityDto> dealBoardActivityDtos;
    private Page<DealCommentActivityDto> dealCommentActivityDtos;
    private Page<ReceivedReportDto> receivedReportDtos;
    private Page<ReportActivityDto> reportActivityDtos;

}
