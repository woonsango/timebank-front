package com.capstone.timepay.domain.dealBoardComment;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReport;
import com.capstone.timepay.domain.dealCommentReport.DealCommentReport;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class DealBoardComment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long d_commentId;

    @Column
    private String content;
    private boolean isApplied;
    private boolean isAdopted;

    @OneToMany(mappedBy = "dealBoardComment", orphanRemoval = true)
    private List<DealCommentReport> dealCommentReports = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="d_board_id")
    private DealBoard dealBoard;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}
