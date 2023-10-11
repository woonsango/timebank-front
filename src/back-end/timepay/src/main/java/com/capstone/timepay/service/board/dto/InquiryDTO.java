package com.capstone.timepay.service.board.dto;

import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.inquiry.Inquiry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static InquiryDTO toInquiryDTO(Inquiry inquiry)
    {
        return new InquiryDTO(
                inquiry.getInquiryId(),
                inquiry.getTitle(),
                inquiry.getCategory(),
                inquiry.getState(),
                inquiry.getContent(),
                inquiry.getCreatedAt(),
                inquiry.getUpdatedAt()
        );
    }
}