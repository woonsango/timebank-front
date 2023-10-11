package com.capstone.timepay.controller.admin.response.userManage;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreeBoardActivityDto {

    private Long boardId;
    private LocalDateTime createdAt;
    private String boardType;
    private String boardState;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String title;

}
