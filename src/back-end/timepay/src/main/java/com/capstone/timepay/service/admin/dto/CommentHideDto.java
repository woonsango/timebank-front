package com.capstone.timepay.service.admin.dto;

import lombok.*;

import java.util.List;
@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentHideDto {

    List<Long> commentIds;

    public static CommentHideDto of(List<Long> commentIds){
        return new CommentHideDto(commentIds);
    }

}


