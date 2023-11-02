package com.capstone.timepay.service.board.dto;

import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.donateBoard.DonateBoard;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import lombok.*;

import java.security.Principal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonateBoardDTO {
    private Long id;
    private String title;
    private String content;
    private String type;
    private Integer targetTimePay; // 목표 타임페이
    private Integer donateTimePay; // 기부받은 타임페이
    private String category;

    // 유저 정보
    private Long userId;
    private String organizationName;
    private String userNickname;
    private String imageURL;
    private String userType;
    private LocalDateTime createAt;


    public static DonateBoardDTO toDonateDTO(DonateBoard donateBoard)
    {
        return new DonateBoardDTO(
                donateBoard.getId(),
                donateBoard.getTitle(),
                donateBoard.getContent(),
                donateBoard.getType(),
                donateBoard.getTargetTimePay(),
                donateBoard.getDonateTimePay(),
                donateBoard.getCategory(),
                donateBoard.getUserId(),
                donateBoard.getOrganizationName(),
                donateBoard.getUserNickname(),
                donateBoard.getImageURL(),
                "기관 회원",
                donateBoard.getCreatedAt()
        );
    }
}
