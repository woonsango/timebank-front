package com.capstone.timepay.domain.freeBoardComment;

import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public interface FreeBoardCommentRepository extends JpaRepository<FreeBoardComment,Long> {
    List<FreeBoardComment> findAllByFreeBoard(FreeBoard freeBoard);
    List<FreeBoardComment> findAllByUser(User user);
    Page<FreeBoardComment> findAllByUser(User user, Pageable pageable);

}
