package com.capstone.timepay.service.admin.dto;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryAnswerDto {

    private String content;
    private Long inquiryId;



    public static InquiryAnswerDto of(String content, Long inquiryId){
        return new InquiryAnswerDto(content, inquiryId);
    }

}
