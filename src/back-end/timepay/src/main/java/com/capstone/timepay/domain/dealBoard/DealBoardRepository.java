package com.capstone.timepay.domain.dealBoard;

import com.capstone.timepay.domain.freeBoard.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealBoardRepository extends JpaRepository<DealBoard,Long> {
    // 숨김처리 안되어있는것만 조회
    List<DealBoard> findAllByIsHiddenFalse();
    // 숨김처리된 게시물 조회
    List<DealBoard> findAAByIsHiddenTrue();
}
