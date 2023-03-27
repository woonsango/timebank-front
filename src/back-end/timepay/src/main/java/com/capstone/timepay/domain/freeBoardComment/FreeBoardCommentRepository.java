package com.capstone.timepay.domain.freeBoardComment;

import com.capstone.timepay.domain.freeBoard.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBoardCommentRepository extends JpaRepository<FreeBoardComment,Long> {
    List<FreeBoardComment> findAllByFreeBoard(FreeBoard freeBoard);
}
