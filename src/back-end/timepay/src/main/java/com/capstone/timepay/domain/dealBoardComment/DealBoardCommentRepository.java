package com.capstone.timepay.domain.dealBoardComment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealBoardCommentRepository extends JpaRepository<DealBoardComment,Long> {
    List<DealBoardComment> findAllByDealBoardCommentId(Long boardId);
}
