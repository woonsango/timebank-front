package com.capstone.timepay.controller.board.reseponse;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InstantMatchingResponseDTO {
    private String status;
    private Boolean success;

    public InstantMatchingResponseDTO(String status){
        this.status = status;
        this.success = false;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setSuccess(Boolean success)
    {
        this.success = success;
    }
}
