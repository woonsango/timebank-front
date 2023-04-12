package com.capstone.timepay.controller.admin.response.userManage;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FreeCommentActivityDto {

    private Long commentId;
    private LocalDateTime createdAt;
    private boolean isApplied;
    private boolean isAdopted;
    private String content;

}
