package com.capstone.timepay.domain.timetable;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_table_id")
    private TimeTable timeTable;

    private String startTime;
    private String endTime;
    private boolean isAdopted;
    private Long boardId;
}
