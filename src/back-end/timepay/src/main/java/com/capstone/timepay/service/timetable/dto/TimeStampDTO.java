package com.capstone.timepay.service.timetable.dto;

import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.timetable.TimeStamp;
import com.capstone.timepay.domain.timetable.TimeTable;
import com.capstone.timepay.service.board.dto.FreeBoardDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeStampDTO {
    private Long id;
    private String startTime;
    private String endTime;
    private boolean isAdopted;
    private Long boardId;
    private Long userId;

    public static TimeStampDTO toTimeStampDTO(TimeStamp timeStamp)
    {
        return new TimeStampDTO(
                timeStamp.getId(),
                timeStamp.getStartTime(),
                timeStamp.getEndTime(),
                timeStamp.isAdopted(),
                timeStamp.getBoardId(),
                timeStamp.getUserId()
        );
    }
}
