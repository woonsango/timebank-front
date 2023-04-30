package com.capstone.timepay.domain.freeBoard;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.freeAttatchment.FreeAttatchment;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import com.capstone.timepay.domain.freeBoardReport.FreeBoardReport;
import com.capstone.timepay.domain.freeRegister.FreeRegister;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class FreeBoard extends BaseTimeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long f_boardId;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    @Column(nullable = false)
    private String content;
    private String category;
    private String type;

    // 숨김처리
    @Column
    private boolean isHidden;

    /*
        TODO: 게시글 유형, 상태, 지급 타임페이, 장소 보낼필요있음
        유저 정보도 보내야하는것 아닌가?
     */

    @JsonIgnore
    @OneToMany(mappedBy = "freeBoard", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FreeBoardComment> freeBoardComments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "freeBoard", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FreeAttatchment> freeAttatchments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "freeBoard", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FreeRegister> freeRegisters = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "freeBoard", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FreeBoardReport> freeBoardReports = new ArrayList<>();
}
