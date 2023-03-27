package com.capstone.timepay.service.board.dto;

import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
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
    private Long uid;    // 일단 uuid로 쏨
    private LocalDateTime createdAt;

    public static FreeBoardCommentDTO toFreeBoardCommentDTO(FreeBoardComment freeBoardComment)
    {
        return new FreeBoardCommentDTO(
                freeBoardComment.getF_commentId(),
                freeBoardComment.getContent(),
                freeBoardComment.getUid(),
                freeBoardComment.getCreatedAt()
        );
    }
}
