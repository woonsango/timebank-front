package com.capstone.timepay.domain.dealBoardReport;

import com.capstone.timepay.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealBoardReportRepository extends JpaRepository<DealBoardReport,Long> {
    List<DealBoardReport> findAllByUser(User user);
}
