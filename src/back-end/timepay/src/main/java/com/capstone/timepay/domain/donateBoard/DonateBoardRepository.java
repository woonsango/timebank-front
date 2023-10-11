package com.capstone.timepay.domain.donateBoard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonateBoardRepository extends JpaRepository<DonateBoard, Long> {
    Page<DonateBoard> findByUserId(Long userId, Pageable pageable);
}
