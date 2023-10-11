package com.capstone.timepay.service.board.dto;

import com.capstone.timepay.domain.board.BoardStatus;
import com.capstone.timepay.domain.dealAttatchment.DealAttatchment;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.user.User;
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
public class DealBoardDTO
{
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
    private List<DealAttatchment> images;
    private int volunteerPeople;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private String userName;
    private String userNickname;
    private boolean isVolunteer;
    private String organizationName;
    private String organizationImage;
    private int volunteerTime;

    public static DealBoardDTO toDealBoardDTO(DealBoard dealBoard)
    {
        if (dealBoard.getDealRegisters().get(0).getUser().getOrganization() != null)
        {
            return new DealBoardDTO(
                    dealBoard.getD_boardId(),
                    dealBoard.getTitle(),
                    dealBoard.getBoardStatus(),
                    dealBoard.getType(),
                    dealBoard.getContent(),
                    dealBoard.getCategory(),
                    dealBoard.getLocation(),
                    dealBoard.getStartTime(),
                    dealBoard.getEndTime(),
                    dealBoard.getPay(),
                    dealBoard.isHidden(),
                    dealBoard.isAuto(),
                    dealBoard.getDealAttatchments(),
                    dealBoard.getVolunteerPeople(),
                    dealBoard.getCreatedAt(),
                    dealBoard.getUpdatedAt(),
                    dealBoard.getDealRegisters().get(0).getUser().getUserId(),
                    dealBoard.getDealRegisters().get(0).getUser().getName(),
                    dealBoard.getDealRegisters().get(0).getUser().getNickname(),
                    dealBoard.isVolunteer(),
                    dealBoard.getDealRegisters().get(0).getUser().getOrganization().getOrganizationName(),
                    dealBoard.getDealRegisters().get(0).getUser().getOrganization().getImageUrl(),
                    dealBoard.getVolunteerTime()
            );
        }
        else if (dealBoard.getDealRegisters() != null && !dealBoard.getDealRegisters().isEmpty()) {
            return new DealBoardDTO(
                    dealBoard.getD_boardId(),
                    dealBoard.getTitle(),
                    dealBoard.getBoardStatus(),
                    dealBoard.getType(),
                    dealBoard.getContent(),
                    dealBoard.getCategory(),
                    dealBoard.getLocation(),
                    dealBoard.getStartTime(),
                    dealBoard.getEndTime(),
                    dealBoard.getPay(),
                    dealBoard.isHidden(),
                    dealBoard.isAuto(),
                    dealBoard.getDealAttatchments(),
                    dealBoard.getVolunteerPeople(),
                    dealBoard.getCreatedAt(),
                    dealBoard.getUpdatedAt(),
                    dealBoard.getDealRegisters().get(0).getUser().getUserId(),
                    dealBoard.getDealRegisters().get(0).getUser().getName(),
                    dealBoard.getDealRegisters().get(0).getUser().getNickname(),
                    dealBoard.isVolunteer(),
                    null,
                    null,
                    dealBoard.getVolunteerTime()
            );
        } else {
            return new DealBoardDTO(
                    dealBoard.getD_boardId(),
                    dealBoard.getTitle(),
                    dealBoard.getBoardStatus(),
                    dealBoard.getType(),
                    dealBoard.getContent(),
                    dealBoard.getCategory(),
                    dealBoard.getLocation(),
                    dealBoard.getStartTime(),
                    dealBoard.getEndTime(),
                    dealBoard.getPay(),
                    dealBoard.isHidden(),
                    dealBoard.isAuto(),
                    dealBoard.getDealAttatchments(),
                    dealBoard.getVolunteerPeople(),
                    dealBoard.getCreatedAt(),
                    dealBoard.getUpdatedAt(),
                    null,
                    null,
                    null,
                    dealBoard.isVolunteer(),
                    null,
                    null,
                    dealBoard.getVolunteerTime()
            );
        }
    }

    public static DealBoardDTO toDealBoardAndUserDTO(DealBoard dealBoard, User user) {
        if (user.getOrganization() == null) {
            return new DealBoardDTO(
                    dealBoard.getD_boardId(),
                    dealBoard.getTitle(),
                    dealBoard.getBoardStatus(),
                    dealBoard.getType(),
                    dealBoard.getContent(),
                    dealBoard.getCategory(),
                    dealBoard.getLocation(),
                    dealBoard.getStartTime(),
                    dealBoard.getEndTime(),
                    dealBoard.getPay(),
                    dealBoard.isHidden(),
                    dealBoard.isAuto(),
                    dealBoard.getDealAttatchments(),
                    dealBoard.getVolunteerPeople(),
                    dealBoard.getCreatedAt(),
                    dealBoard.getUpdatedAt(),
                    user.getUserId(),
                    user.getName(),
                    user.getNickname(),
                    dealBoard.isVolunteer(),
                    null,
                    null,
                    dealBoard.getVolunteerTime()
            );
        } else {
            return new DealBoardDTO(
                    dealBoard.getD_boardId(),
                    dealBoard.getTitle(),
                    dealBoard.getBoardStatus(),
                    dealBoard.getType(),
                    dealBoard.getContent(),
                    dealBoard.getCategory(),
                    dealBoard.getLocation(),
                    dealBoard.getStartTime(),
                    dealBoard.getEndTime(),
                    dealBoard.getPay(),
                    dealBoard.isHidden(),
                    dealBoard.isAuto(),
                    dealBoard.getDealAttatchments(),
                    dealBoard.getVolunteerPeople(),
                    dealBoard.getCreatedAt(),
                    dealBoard.getUpdatedAt(),
                    user.getUserId(),
                    user.getName(),
                    user.getNickname(),
                    dealBoard.isVolunteer(),
                    user.getOrganization().getOrganizationName(),
                    user.getOrganization().getImageUrl(),
                    dealBoard.getVolunteerTime()
            );
        }
    }
}
