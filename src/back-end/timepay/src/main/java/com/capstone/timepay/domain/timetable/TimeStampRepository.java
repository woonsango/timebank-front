package com.capstone.timepay.domain.timetable;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeStampRepository extends JpaRepository<TimeStamp, Long> {
    List<TimeStamp> findByStartTimeBetween(String startTime, String endTime);
}
