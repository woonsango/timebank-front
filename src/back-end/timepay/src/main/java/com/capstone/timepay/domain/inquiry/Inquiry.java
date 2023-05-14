package com.capstone.timepay.domain.inquiry;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import com.capstone.timepay.domain.inquiryAnswer.InquiryAnswer;
import com.capstone.timepay.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Inquiry extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryId;

    @Column
    private String title;
    private String category;
    private String state;
    private String content;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "inquiry", orphanRemoval = true)
    private List<InquiryAnswer> inquiryAnswers = new ArrayList<>();

    public void updateStateComplete(){
        this.state = "답변 완료";
    }
}
