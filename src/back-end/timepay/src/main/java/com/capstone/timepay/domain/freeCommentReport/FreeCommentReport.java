package com.capstone.timepay.domain.freeCommentReport;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
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
public class FreeCommentReport extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fc_reportId;

    @Column
    private String content;
    private String process;
    @ManyToOne
    @JoinColumn(name="f_comment_id")
    private FreeBoardComment freeBoardComment;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
