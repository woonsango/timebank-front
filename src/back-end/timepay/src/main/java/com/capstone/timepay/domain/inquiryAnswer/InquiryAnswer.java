package com.capstone.timepay.domain.inquiryAnswer;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.domain.inquiry.Inquiry;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.service.admin.dto.InquiryAnswerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class InquiryAnswer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryAnswerId;

    private String content;

    @ManyToOne
    @JoinColumn(name="inquiry_id")
    private Inquiry inquiry;

    @ManyToOne
    @JoinColumn(name="admin_id")
    private Admin admin;

    public static InquiryAnswer getNewInstance(InquiryAnswerDto answer, Inquiry inquiry, Admin admin){
        return InquiryAnswer.builder()
                .content(answer.getContent())
                .inquiry(inquiry)
                .admin(admin)
                .build();
    }
}
