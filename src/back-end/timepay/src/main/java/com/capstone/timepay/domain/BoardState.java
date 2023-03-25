package com.capstone.timepay.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardState {
    BOARD_WAIT("활동 대기"),
    BOARD_RUNNING("활동 중"),
    BOARD_FINISH("활동 종료");

    private final String state;
}