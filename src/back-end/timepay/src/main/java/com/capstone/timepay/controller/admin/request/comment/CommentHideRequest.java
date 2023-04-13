package com.capstone.timepay.controller.admin.request.comment;

import com.capstone.timepay.service.admin.dto.CommentHideDto;
import lombok.*;

import java.util.List;


@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentHideRequest {
    List<Long> commentIds;

    public CommentHideDto toServiceDto(){
        return CommentHideDto.of(commentIds);
    }
}

