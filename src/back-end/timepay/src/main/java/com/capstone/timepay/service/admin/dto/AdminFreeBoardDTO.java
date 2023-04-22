package com.capstone.timepay.service.admin.dto;

import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminFreeBoardDTO {

    private Long id;
    private String title;
    private String content;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isHidden;

    private List<FreeBoardComment> freeBoardComments;

    public static AdminFreeBoardDTO toFreeBoardDTO(FreeBoard freeBoard)
    {
        return new AdminFreeBoardDTO(
                freeBoard.getF_boardId(),
                freeBoard.getTitle(),
                freeBoard.getContent(),
                freeBoard.getCategory(),
                freeBoard.getCreatedAt(),
                freeBoard.getUpdatedAt(),
                freeBoard.isHidden(),
                freeBoard.getFreeBoardComments()
        );
    }
}
