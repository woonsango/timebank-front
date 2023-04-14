package com.capstone.timepay.domain.board;

public enum BoardStatus {
    FREE_BOARD,                   // 자유게시판일때
    MATCHING_IN_PROGRESS, // 매칭 중
    MATCHING_COMPLETE,    // 매칭 완료
    ACTIVITY_IN_PROGRESS, // 활동 중
    ACTIVITY_COMPLETE,     // 활동 완료
    ACTIVITY_DELAY,         // 활동 지연
    ACTIVITY_CANCEL         // 활동 취소
}
