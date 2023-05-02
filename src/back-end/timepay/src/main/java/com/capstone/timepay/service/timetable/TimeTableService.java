package com.capstone.timepay.service.timetable;

import com.capstone.timepay.domain.timetable.TimeStamp;
import com.capstone.timepay.domain.timetable.TimeStampRepository;
import com.capstone.timepay.service.timetable.dto.TimeStampDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeTableService {
    private final TimeStampRepository timeStampRepository;

    public List<TimeStamp> getAllTimeStamps() {
        return timeStampRepository.findAll();
    }

    public List<TimeStampDTO> getTimeStamp(String startTime, String endTime)
    {
        List<TimeStamp> timeStamps = timeStampRepository.findByStartTimeBetween(startTime, endTime);
        return timeStamps.stream()
                .map(timeStamp -> TimeStampDTO.builder()
                        .id(timeStamp.getId())
                        .startTime(timeStamp.getStartTime())
                        .endTime(timeStamp.getEndTime())
                        .isAdopted(timeStamp.isAdopted())
                        .boardId(timeStamp.getBoardId())
                        .build())
                .collect(Collectors.toList());
    }


    public TimeStampDTO addTimeStamp(TimeStampDTO timeStampDTO)
    {
        TimeStamp timestamp = TimeStamp.builder()
                .id(timeStampDTO.getId())
                .startTime(timeStampDTO.getStartTime())
                .endTime(timeStampDTO.getEndTime())
                .isAdopted(timeStampDTO.isAdopted())
                .boardId(timeStampDTO.getBoardId())
                .userId(timeStampDTO.getUserId())
                .build();
        timeStampRepository.save(timestamp);

        return TimeStampDTO.toTimeStampDTO(timestamp);
    }

    public void deleteTimeStamp(Long timestampId)
    {
        TimeStamp timeStamp = timeStampRepository.findById(timestampId).orElseThrow(() ->{
                    return new IllegalArgumentException("TimeStamp를 찾을 수 없습니다");
                });
        timeStampRepository.deleteById(timestampId);
    }
}
