package com.capstone.timepay.domain.freeBoard;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.dealAttatchment.DealAttatchment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReport;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.freeAttatchment.FreeAttatchment;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import com.capstone.timepay.domain.freeBoardReport.FreeBoardReport;
import com.capstone.timepay.domain.freeRegister.FreeRegister;
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
public class FreeBoard extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long f_boardId;

    @Column
    private String title;
    private String content;
    private String category;

    @OneToMany(mappedBy = "freeBoard", orphanRemoval = true)
    private List<FreeBoardComment> freeBoardComments = new ArrayList<>();

    @OneToMany(mappedBy = "freeBoard", orphanRemoval = true)
    private List<FreeAttatchment> freeAttatchments = new ArrayList<>();

    @OneToMany(mappedBy = "freeBoard", orphanRemoval = true)
    private List<FreeRegister> freeRegisters = new ArrayList<>();

    @OneToMany(mappedBy = "freeBoard", orphanRemoval = true)
    private List<FreeBoardReport> freeBoardReports = new ArrayList<>();
}
