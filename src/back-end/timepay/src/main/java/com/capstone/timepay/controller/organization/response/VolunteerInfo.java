package com.capstone.timepay.controller.organization.response;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerInfo {
    private String title;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int participateNum;
}
