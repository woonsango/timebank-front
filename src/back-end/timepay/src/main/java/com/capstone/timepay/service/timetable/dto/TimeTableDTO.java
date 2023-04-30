package com.capstone.timepay.service.timetable.dto;

import com.capstone.timepay.domain.timetable.TimeStamp;
import com.capstone.timepay.domain.timetable.TimeTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeTableDTO {
    private Long timeTableId;
    private List<TimeStamp> timeStamps = new ArrayList<>();
    private String startTime;
    private String endTime;

    public static TimeTableDTO toTimeTableDTO(TimeTable timeTable)
    {
        return new TimeTableDTO(
                timeTable.getTimeTableId(),
                timeTable.getTimeStamps(),
                timeTable.getStartTime(),
                timeTable.getEndTime()
        );
    }
}
