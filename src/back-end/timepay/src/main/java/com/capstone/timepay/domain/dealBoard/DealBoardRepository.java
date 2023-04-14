package com.capstone.timepay.domain.dealBoard;

import com.capstone.timepay.domain.freeBoard.FreeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealBoardRepository extends JpaRepository<DealBoard,Long> {
    Page<DealBoard> findByIsHiddenFalse(Pageable pageable);
}
