package com.capstone.timepay.controller.timetable;

import com.capstone.timepay.domain.timetable.TimeStamp;
import com.capstone.timepay.domain.timetable.TimeStampRepository;
import com.capstone.timepay.service.timetable.TimeTableService;
import com.capstone.timepay.service.timetable.dto.TimeStampDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/timetable")
public class TimeTableController {
    private final TimeTableService timeTableService;

    @ApiOperation(value = "해당 타임스탬프 모두 조회")
    @GetMapping("/all")
    public List<TimeStampDTO> getAllTimeStampsBetween()
    {
        List<TimeStamp> timeStamps = timeTableService.getAllTimeStamps();
        return timeStamps.stream()
                .map(TimeStampDTO::toTimeStampDTO)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "해당 시간대 타임스탬프 조회")
    @GetMapping
    public List<TimeStampDTO> getTimeStampsBetween(@RequestParam String startTime,
                                                   @RequestParam String endTime)
    {
        return timeTableService.getTimeStamp(startTime, endTime);
    }

    @ApiOperation(value = "타임스탬프 추가")
    @PostMapping("/add")
    public ResponseEntity addTimeStamp(@RequestBody TimeStampDTO timeStampDTO)
    {
        return new ResponseEntity(timeTableService.addTimeStamp(timeStampDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "타임스탬프 삭제")
    @DeleteMapping("/delete/{timestampId}")
    public void deleteTimeStamp(@PathVariable("timestampId") Long timestampId)
    {
        timeTableService.deleteTimeStamp(timestampId);
    }
}
