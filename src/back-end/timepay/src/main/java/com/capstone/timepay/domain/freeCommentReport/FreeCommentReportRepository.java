package com.capstone.timepay.domain.freeCommentReport;

import com.capstone.timepay.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeCommentReportRepository extends JpaRepository<FreeCommentReport,Long> {
    List<FreeCommentReport> findAllByUser(User user);
}
