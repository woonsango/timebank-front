package com.capstone.timepay.domain.dealBoard;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.board.BoardStatus;
import com.capstone.timepay.domain.dealAttatchment.DealAttatchment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReport;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class DealBoard extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long d_boardId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String state;

    @Lob
    @Column(nullable = false)
    private String content;
    private String category;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int pay;

    // 숨김처리
    @Column
    private boolean isHidden;

    private BoardStatus boardStatus;
    private Long uid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "dealBoard", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DealBoardComment> dealBoardComments = new ArrayList<>();

    @OneToMany(mappedBy = "dealBoard", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DealAttatchment> dealAttatchments = new ArrayList<>();

    @OneToMany(mappedBy = "dealBoard", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DealRegister> dealRegisters = new ArrayList<>();

    @OneToMany(mappedBy = "dealBoard", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DealBoardReport> dealBoardReports = new ArrayList<>();
}
