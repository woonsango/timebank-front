package com.capstone.timepay.domain.freeBoardReport;

import com.capstone.timepay.domain.user.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBoardReportRepository extends JpaRepository<FreeBoardReport,Long> {
    List<FreeBoardReport> findAllByUser(User user);

    List<FreeBoardReport> findTop10ByUser(User user, Sort sort);
}
