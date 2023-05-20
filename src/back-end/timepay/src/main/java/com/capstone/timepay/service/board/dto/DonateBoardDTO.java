package com.capstone.timepay.service.board.dto;

import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.donateBoard.DonateBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public static DonateBoardDTO toDonateDTO(DonateBoard donateBoard)
    {
        return new DonateBoardDTO(
                donateBoard.getId(),
                donateBoard.getTitle(),
                donateBoard.getContent(),
                donateBoard.getType(),
                donateBoard.getTargetTimePay(),
                donateBoard.getDonateTimePay(),
                donateBoard.getCategory()
        );
    }
}
