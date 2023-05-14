package com.capstone.timepay.controller.admin.response.board;

import com.capstone.timepay.domain.board.BoardStatus;
import com.capstone.timepay.domain.dealAttatchment.DealAttatchment;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealBoardResponse {
    private Long d_boardId;
    private String title;
    private String content;
    private String type;
    private String category;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int pay;
    private boolean isHidden;
    private boolean isAuto;
    private BoardStatus boardStatus;
    private String state;
    private int volunteerTime;
    private boolean isVolunteer;
    private int volunteerPeople;
    private List<DealAttatchment> dealAttatchments;

    private Long userId;
    private String userName;
    private String userNickname;

    public DealBoardResponse(DealBoard dealBoard) {
        this.d_boardId = dealBoard.getD_boardId();
        this.title = dealBoard.getTitle();
        this.content = dealBoard.getContent();
        this.type = dealBoard.getType();
        this.category = dealBoard.getCategory();
        this.location = dealBoard.getLocation();
        this.startTime = dealBoard.getStartTime();
        this.endTime = dealBoard.getEndTime();
        this.pay = dealBoard.getPay();
        this.isHidden = dealBoard.isHidden();
        this.isAuto = dealBoard.isAuto();
        this.boardStatus = dealBoard.getBoardStatus();
        this.state = dealBoard.getState();
        this.volunteerTime = dealBoard.getVolunteerTime();
        this.isVolunteer = dealBoard.isVolunteer();
        this.volunteerPeople = dealBoard.getVolunteerPeople();
        this.dealAttatchments = dealBoard.getDealAttatchments();
        this.userId = dealBoard.getDealRegisters().get(0).getUser().getUserId();
        this.userName = dealBoard.getDealRegisters().get(0).getUser().getName();
        this.userNickname = dealBoard.getDealRegisters().get(0).getUser().getNickname();
    }
}
