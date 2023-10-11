package com.capstone.timepay.service.admin.dto;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PenaltyUserDto {

    List<Long> userIds;

    public static PenaltyUserDto of(List<Long> userIds){
        return new PenaltyUserDto(userIds);
    }

}
