package com.capstone.timepay.domain.dealCommentReport;

import com.capstone.timepay.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealCommentReportRepository extends JpaRepository<DealCommentReport,Long> {
    List<DealCommentReport> findAllByUser(User user);
}
