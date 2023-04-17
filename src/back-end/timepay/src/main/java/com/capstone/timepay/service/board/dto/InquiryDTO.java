package com.capstone.timepay.service.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryDTO {
    private Long inquiryId;
    private String title;
    private String category;
    private String state;
    private String content;
}