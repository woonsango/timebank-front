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
import com.capstone.timepay.service.board.dto.FreeBoardDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "fboard")
public class FreeBoard extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String freeWriter;
    // 나중에 유저가 생성되면 User클래스로 수정해줘야함

    @Column
    private String freeTitle;

    @Column
    private String freeContent;

    @Column
    private String freeCategory;

    // 조회수
    @Column
    private int freeBoardHits;

    @OneToMany(mappedBy = "freeBoard", orphanRemoval = true)
    private List<FreeBoardComment> freeBoardComments = new ArrayList<>();

    @OneToMany(mappedBy = "freeBoard", orphanRemoval = true)
    private List<FreeAttatchment> freeAttatchments = new ArrayList<>();

    @OneToMany(mappedBy = "freeBoard", orphanRemoval = true)
    private List<FreeRegister> freeRegisters = new ArrayList<>();

    @OneToMany(mappedBy = "freeBoard", orphanRemoval = true)
    private List<FreeBoardReport> freeBoardReports = new ArrayList<>();


    public static FreeBoard toSaveFreeEntity(FreeBoardDTO freeBoardDTO)
    {
        FreeBoard freeBoardEntity = new FreeBoard();
        freeBoardEntity.setFreeWriter(freeBoardDTO.getFreeWriter());
        freeBoardEntity.setFreeTitle(freeBoardDTO.getFreeTitle());
        freeBoardEntity.setFreeCategory(freeBoardDTO.getFreeCategory());
        freeBoardEntity.setFreeContent(freeBoardDTO.getFreeContent());
        freeBoardEntity.setFreeBoardHits(0);
        return freeBoardEntity;
    }

    public static FreeBoard toUpdateFreeEntity(FreeBoardDTO boardDTO)
    {
        FreeBoard boardEntity = new FreeBoard();
        boardEntity.setId(boardDTO.getId()); // 중요
        boardEntity.setFreeWriter(boardDTO.getFreeWriter());
        boardEntity.setFreeTitle(boardDTO.getFreeTitle());
        boardEntity.setFreeContent(boardDTO.getFreeContent());
        boardEntity.setFreeCategory(boardDTO.getFreeCategory());
        boardEntity.setFreeBoardHits(boardEntity.getFreeBoardHits());
        return boardEntity;
    }
}
