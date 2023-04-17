package com.capstone.timepay.domain.dealCommentReport;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.user.User;
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
public class DealCommentReport extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dc_reportId;

    @Column
    private String content;
    private String process;
    @ManyToOne
    @JoinColumn(name="d_comment_id")
    private DealBoardComment dealBoardComment;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
