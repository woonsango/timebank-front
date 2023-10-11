package com.capstone.timepay.controller.admin.response.inquiry;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryResponse {

    private Long inquiryId;
    private String state;
    private String category;
    private LocalDateTime createdAt;
    private String writer;
    private String title;
    private String content;

}
