package com.capstone.timepay.service.admin.dto;

import com.capstone.timepay.domain.board.BoardStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdminBoardStatusDto {
    private List<Long> ids;
    private BoardStatus status;
}
