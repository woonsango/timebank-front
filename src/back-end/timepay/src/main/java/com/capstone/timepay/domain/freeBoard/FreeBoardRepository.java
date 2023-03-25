package com.capstone.timepay.domain.freeBoard;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBoardRepository extends JpaRepository<FreeBoard,Long> {
    // 숨김처리 안되어있는것만 조회
    List<FreeBoard> findAllByIsHiddenFalse();
    // 숨김처리된 게시물 조회
    List<FreeBoard> findAAByIsHiddenTrue();
}
