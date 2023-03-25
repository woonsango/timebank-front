package com.capstone.timepay.domain.dealBoardComment;

import com.capstone.timepay.domain.dealBoard.DealBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealBoardCommentRepository extends JpaRepository<DealBoardComment,Long> {
    List<DealBoardComment> findAllByDealBoard(DealBoard dealBoard);
}
