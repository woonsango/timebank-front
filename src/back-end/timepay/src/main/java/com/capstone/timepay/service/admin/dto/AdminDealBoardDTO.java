package com.capstone.timepay.service.admin.dto;

import com.capstone.timepay.domain.board.BoardStatus;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDealBoardDTO {

    private Long dealBoardId;
    private String title;
    private BoardStatus boardState;

    private String content;
    private String category;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int pay;
    private boolean isHidden;

    private List<DealBoardComment> dealBoardComments;

    public static AdminDealBoardDTO toDealBoardDTO(DealBoard dealBoard)
    {
        return new AdminDealBoardDTO(
                dealBoard.getD_boardId(),
                dealBoard.getTitle(),
                dealBoard.getBoardStatus(),
                dealBoard.getContent(),
                dealBoard.getCategory(),
                dealBoard.getLocation(),
                dealBoard.getStartTime(),
                dealBoard.getEndTime(),
                dealBoard.getCreatedAt(),
                dealBoard.getUpdatedAt(),
                dealBoard.getPay(),
                dealBoard.isHidden(),
                dealBoard.getDealBoardComments()
        );
    }
}
