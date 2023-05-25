package com.capstone.timepay.service.board.dto;

import com.capstone.timepay.domain.freeBoard.FreeBoard;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FreeBoardDTO {
    private Long id; // pk
    private LocalDateTime freeCreatedAt;    // 생성날짜
    private LocalDateTime freeUpdatedAt;    // 수정날짜
    private String freeTitle;               // 제목
    private String freeContent;             // 내용
    private String freeWriter;              // 작성자 (나중에 유저 클래스로 변경)
    private String freeCategory;            // 카테고리
    private int freeBoardHits;           // 조회수

//    public static BoardDTO toBoardDTO();

    public static FreeBoardDTO toFreeBoardDTO(FreeBoard freeBoard)
    {
        FreeBoardDTO freeBoardDTO = new FreeBoardDTO();
        freeBoardDTO.setId(freeBoard.getId());
        freeBoardDTO.setFreeWriter(freeBoard.getFreeWriter());
        freeBoardDTO.setFreeTitle(freeBoard.getFreeTitle());
        freeBoardDTO.setFreeContent(freeBoard.getFreeContent());
        freeBoardDTO.setFreeCategory(freeBoard.getFreeCategory());
        freeBoardDTO.setFreeBoardHits(freeBoard.getFreeBoardHits());
        freeBoardDTO.setFreeCreatedAt(freeBoard.getCreatedAt());
        freeBoardDTO.setFreeUpdatedAt(freeBoard.getUpdatedAt());
        return freeBoardDTO;
    }
}
