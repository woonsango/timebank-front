package com.capstone.timepay.controller.admin.response.userManage;

import com.capstone.timepay.domain.board.BoardStatus;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealBoardActivityDto {

    private Long boardId;
    private LocalDateTime createdAt;
    private String boardType;
    private BoardStatus boardState;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime actualStartTime;
    private LocalDateTime actualEndTime;
    private String title;
    private boolean isVolunteer;

}
