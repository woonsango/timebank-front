package com.capstone.timepay.service.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long boardId;
    private FreeBoardDTO freeBoardDTO;
    private DealBoardDTO dealBoardDTO;
}
