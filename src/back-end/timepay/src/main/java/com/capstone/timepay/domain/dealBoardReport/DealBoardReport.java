package com.capstone.timepay.domain.dealBoardReport;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.dealBoard.DealBoard;
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
public class DealBoardReport extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long db_reportId;

    @Column
    private String content;
    private String process;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="deal_board_id")
    private DealBoard dealBoard;

    public DealBoardReport(User user, String content, DealBoard dealBoard){
        this.user = user;
        this.content = content;
        this.dealBoard = dealBoard;
    }
}
