package com.capstone.timepay.controller.admin.response;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentActivityDto {

    private Long commentId;
    private LocalDateTime createdAt;
    private String applyYN;
    private String confirmYN;
    private String content;

}
