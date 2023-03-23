package com.capstone.timepay.service.board.dto;

import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealBoardCommentDTO {
    private Long id;
    private String content;
    private String writer;
    private boolean isApplied;
    private boolean isAdopted;
    private boolean isHidden;

    public static DealBoardCommentDTO toDealBoardCommentDTO(DealBoardComment dealBoardComment)
    {
        return new DealBoardCommentDTO(
                dealBoardComment.getDealBoardCommentId(),
                dealBoardComment.getContent(),
                dealBoardComment.getTestUser().getName(),
                dealBoardComment.isApplied(),
                dealBoardComment.isAdopted(),
                dealBoardComment.isHidden()
        );
    }
}
