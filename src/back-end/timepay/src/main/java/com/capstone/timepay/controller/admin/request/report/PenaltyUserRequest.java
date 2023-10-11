package com.capstone.timepay.controller.admin.request.report;

import com.capstone.timepay.service.admin.dto.PenaltyUserDto;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PenaltyUserRequest {

    List<Long> userIds;

    public PenaltyUserDto toServiceDto(){
        return PenaltyUserDto.of(userIds);
    }

}
