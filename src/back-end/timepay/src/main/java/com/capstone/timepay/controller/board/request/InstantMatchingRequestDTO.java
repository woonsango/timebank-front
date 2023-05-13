package com.capstone.timepay.controller.board.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InstantMatchingRequestDTO {
    private String email;

    public InstantMatchingRequestDTO(String email){
        this.email = email;
    }
}