package com.capstone.timepay.service.admin.dto;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryAnswerDto {

    private String content;
    private Long adminId;
    private Long inquiryId;



    public static InquiryAnswerDto of(String content, Long adminId, Long inquiryId){
        return new InquiryAnswerDto(content,adminId, inquiryId);
    }

}
