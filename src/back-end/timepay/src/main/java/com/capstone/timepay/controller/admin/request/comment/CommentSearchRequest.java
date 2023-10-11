package com.capstone.timepay.controller.admin.request.comment;

import com.capstone.timepay.service.admin.dto.CommentSearchDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentSearchRequest {
    Long commentId;
    String name;
    String nickname;

    public CommentSearchDto toServiceDto(){
        return CommentSearchDto.of(commentId, name, nickname);
    }
}
