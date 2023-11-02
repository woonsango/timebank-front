package com.capstone.timepay.service.board.dto;

import com.capstone.timepay.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonateDTO {
    private User user;  // 타임페이 기부한 유저
    private Integer donateTimePay; // 기부한 타임페이
}
