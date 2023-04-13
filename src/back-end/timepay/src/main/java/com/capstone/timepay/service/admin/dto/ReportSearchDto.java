package com.capstone.timepay.service.admin.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportSearchDto {

    Long reportId;
    String name;
    String content;
    LocalDateTime startTime;
    LocalDateTime endTime;

    public static ReportSearchDto of(Long reportId, String name, String content, LocalDateTime startTime, LocalDateTime endTime){
        return new ReportSearchDto(reportId, name, content, startTime, endTime);
    }

}
