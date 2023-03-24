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
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class FreeBoard extends BaseTimeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    @Column(nullable = false)
    private String content;
    private String category;

    // 숨김처리
    @Column
    private boolean isHidden;

    /*
        TODO: 게시글 유형, 상태, 지급 타임페이, 장소 보낼필요있음
        유저 정보도 보내야하는것 아닌가?
     */

    @OneToMany(mappedBy = "freeBoard", orphanRemoval = true)
    private List<FreeBoardComment> freeBoardComments = new ArrayList<>();

    @OneToMany(mappedBy = "freeBoard", orphanRemoval = true)
    private List<FreeAttatchment> freeAttatchments = new ArrayList<>();

    @OneToMany(mappedBy = "freeBoard", orphanRemoval = true)
    private List<FreeRegister> freeRegisters = new ArrayList<>();

    @OneToMany(mappedBy = "freeBoard", orphanRemoval = true)
    private List<FreeBoardReport> freeBoardReports = new ArrayList<>();
}
