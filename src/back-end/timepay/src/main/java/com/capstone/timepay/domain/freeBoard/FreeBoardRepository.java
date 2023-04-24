package com.capstone.timepay.domain.freeBoard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBoardRepository extends JpaRepository<FreeBoard,Long> {
    Page<FreeBoard> findByIsHiddenFalse(Pageable pageable);
    FreeBoard findByType(String type);
}
