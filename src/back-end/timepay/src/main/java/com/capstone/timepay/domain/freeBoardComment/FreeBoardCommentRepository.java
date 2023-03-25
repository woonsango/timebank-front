package com.capstone.timepay.domain.freeBoardComment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBoardCommentRepository extends JpaRepository<FreeBoardComment,Long> {
    List<FreeBoardComment> findAllByFreeBoardId(Long freeBoardId);
}
