package com.capstone.timepay.service.board.dto;

import com.capstone.timepay.domain.dealBoard.DealBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DealBoardDTO
{
    private Long dealBoardId;
    private String title;
    private String state;
    private String content;
    private String category;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int pay;
    private boolean isHidden;

    public static DealBoardDTO toDealBoardDTO(DealBoard dealBoard)
    {
        return new DealBoardDTO(
                dealBoard.getD_boardId(),
                dealBoard.getTitle(),
                dealBoard.getState(),
                dealBoard.getContent(),
                dealBoard.getCategory(),
                dealBoard.getLocation(),
                dealBoard.getStartTime(),
                dealBoard.getEndTime(),
                dealBoard.getCreatedAt(),
                dealBoard.getUpdatedAt(),
                dealBoard.getPay(),
                dealBoard.isHidden()
        );
    }
}
