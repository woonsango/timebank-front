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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long db_reportId;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="d_board_id")
    private DealBoard dealBoard;
}
