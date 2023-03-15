package com.capstone.timepay.domain.dealBoard;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.dealAttatchment.DealAttatchment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReport;
import com.capstone.timepay.domain.dealCommentReport.DealCommentReport;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.notification.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class DealBoard extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long d_boardId;

    @Column
    private String title;
    private String state;
    private String content;
    private String category;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int pay;

    @OneToMany(mappedBy = "dealBoard", orphanRemoval = true)
    private List<DealBoardComment> dealBoardComments = new ArrayList<>();

    @OneToMany(mappedBy = "dealBoard", orphanRemoval = true)
    private List<DealAttatchment> dealAttatchments = new ArrayList<>();

    @OneToMany(mappedBy = "dealBoard", orphanRemoval = true)
    private List<DealRegister> dealRegisters = new ArrayList<>();

    @OneToMany(mappedBy = "dealBoard", orphanRemoval = true)
    private List<DealBoardReport> dealBoardReports = new ArrayList<>();
}
