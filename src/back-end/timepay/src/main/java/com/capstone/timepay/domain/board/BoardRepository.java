package com.capstone.timepay.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {
    Optional<Board> findByFBoardId(Long f_boardId);
}
