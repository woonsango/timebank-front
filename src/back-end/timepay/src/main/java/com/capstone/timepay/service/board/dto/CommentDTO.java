package com.capstone.timepay.service.board.dto;

import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long commentId;
    private FreeBoardCommentDTO freeBoardCommentDTO;
    private DealBoardCommentDTO dealBoardCommentDTO;

}
