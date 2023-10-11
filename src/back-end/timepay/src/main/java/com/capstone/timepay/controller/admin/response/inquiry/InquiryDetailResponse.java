package com.capstone.timepay.controller.admin.response.inquiry;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryDetailResponse {

    private InquiryResponse inquiryResponse;

    private List<AdminAnswerResponse> adminAnswerResponses;

}
