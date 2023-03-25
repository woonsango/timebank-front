package com.capstone.timepay.controller.admin.response.comment;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private Long commentId;
    private Long originBoardId;
    private Long originCommentId;
    private String writerName;
    private Long writerId;
    private boolean applyYN;
    private boolean selectYN;
    private boolean originWriterYN;
    private LocalDateTime writtenTime;
    private String content;

}
