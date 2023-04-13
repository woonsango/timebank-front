package com.capstone.timepay.controller.admin.request.report;

import com.capstone.timepay.service.admin.dto.CommentSearchDto;
import com.capstone.timepay.service.admin.dto.ReportSearchDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportSearchRequest {
    Long reportId;
    String name;
    String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime endTime;

    public ReportSearchDto toServiceDto(){
        return ReportSearchDto.of(reportId, name, content, startTime, endTime);
    }
}
