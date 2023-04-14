package com.capstone.timepay.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findByUid(Long uid);

    List<Board> getBoardsByUidAndCategory(Long uid, String category);

    Page<Board> findByIsHiddenFalse(Pageable pageable);
}
