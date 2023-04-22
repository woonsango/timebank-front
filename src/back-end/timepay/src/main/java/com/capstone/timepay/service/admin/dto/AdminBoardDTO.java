package com.capstone.timepay.service.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminBoardDTO {

    private Long boardId;
    private AdminFreeBoardDTO freeBoardDTO;
    private AdminDealBoardDTO dealBoardDTO;

}
