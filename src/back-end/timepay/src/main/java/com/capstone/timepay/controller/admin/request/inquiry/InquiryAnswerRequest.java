package com.capstone.timepay.controller.admin.request.inquiry;

import com.capstone.timepay.service.admin.dto.InquiryAnswerDto;
import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryAnswerRequest {

    @NotNull
    private String content;

    @NotNull
    private Long inquiryId;

    public InquiryAnswerDto toServiceDto(){
        return InquiryAnswerDto.of(content, inquiryId);
    }
}
