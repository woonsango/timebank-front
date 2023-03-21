package com.capstone.timepay.service.board.dto;

import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FreeBoardCommentDTO {
    private Long freeBoardCommentId;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;

    public static FreeBoardCommentDTO toFreeBoardCommentDTO(FreeBoardComment freeBoardComment)
    {
        return new FreeBoardCommentDTO(
                freeBoardComment.getFreeBoardCommentId(),
                freeBoardComment.getContent(),
                freeBoardComment.getUser().getNickname(),
                freeBoardComment.getCreatedAt()
        );
    }
}
