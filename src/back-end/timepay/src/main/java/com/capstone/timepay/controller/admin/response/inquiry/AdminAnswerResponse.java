package com.capstone.timepay.controller.admin.response.inquiry;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminAnswerResponse {
    private String content;
    private String adminName;
    private LocalDateTime createdAt;
}
