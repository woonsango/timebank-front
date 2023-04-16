package com.capstone.timepay.controller.board.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ReportRequestDTO {
    private Long uid;
    private String reportBody;

    public ReportRequestDTO(Long uid, String reportBody){
        this.uid = uid;
        this.reportBody = reportBody;
    }

}
