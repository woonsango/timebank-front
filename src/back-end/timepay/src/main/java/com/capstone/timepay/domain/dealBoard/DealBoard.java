package com.capstone.timepay.domain.dealBoard;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.board.BoardStatus;
import com.capstone.timepay.domain.dealAttatchment.DealAttatchment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReport;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Lob
    @Column(nullable = false)
    private String content;
    private String type;
    private String category;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int pay;
    private boolean isHidden;
    private boolean isAuto;
    private BoardStatus boardStatus;
    private String state;
    private int volunteerTime;
    private boolean isVolunteer;
    private int volunteerPeople;

    // 검색화면을 위한 컬럼
    private String writerName;
    private String writerNickname;
    private String writerType;
    private String imageUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "dealBoard", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DealBoardComment> dealBoardComments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "dealBoard", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DealAttatchment> dealAttatchments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "dealBoard", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DealRegister> dealRegisters = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "dealBoard", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DealBoardReport> dealBoardReports = new ArrayList<>();
}
