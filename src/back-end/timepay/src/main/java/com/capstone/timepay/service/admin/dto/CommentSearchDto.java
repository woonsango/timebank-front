package com.capstone.timepay.service.admin.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentSearchDto {

    Long commentId;
    String name;
    String nickname;

    public static CommentSearchDto of(Long commentId, String name, String nickname){
        return new CommentSearchDto(commentId, name, nickname);
    }

}
