package com.capstone.timepay.domain.dealBoard;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.dealAttatchment.DealAttatchment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReport;
import com.capstone.timepay.domain.dealCommentReport.DealCommentReport;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.notification.Notification;
import com.capstone.timepay.domain.user.TestUser;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long dealBoardId;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private TestUser testUser;

    @OneToMany(mappedBy = "dealBoard", orphanRemoval = true)
    private List<DealBoardComment> dealBoardComments = new ArrayList<>();

    @OneToMany(mappedBy = "dealBoard", orphanRemoval = true)
    private List<DealAttatchment> dealAttatchments = new ArrayList<>();

    @OneToMany(mappedBy = "dealBoard", orphanRemoval = true)
    private List<DealRegister> dealRegisters = new ArrayList<>();

    @OneToMany(mappedBy = "dealBoard", orphanRemoval = true)
    private List<DealBoardReport> dealBoardReports = new ArrayList<>();
}
