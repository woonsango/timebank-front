package com.capstone.timepay.controller.board.request;

import com.capstone.timepay.domain.board.BoardStatus;
import com.capstone.timepay.domain.dealAttatchment.DealAttatchment;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.service.board.dto.DealBoardDTO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InstantMatchingBoardDTO {

    private Long dealBoardId;
    private String title;
    private BoardStatus state;
    private String type;
    private String content;
    private String category;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int pay;
    private boolean isHidden;
    private boolean isAuto;
    private String email;
}
