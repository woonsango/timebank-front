package com.capstone.timepay.controller.admin.request.auth;

import com.capstone.timepay.service.admin.dto.AdminRegisterDTO;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRegisterRequest {

    @NotNull
    private String adminName;   // 로그인 할 때 쓰는 아이디

    @NotNull
    private String password;

    @NotNull
    private String name;    // 관리자 이름

    private String email;
    private String phone;

    public AdminRegisterDTO toServiceDto() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, AdminRegisterDTO.class);
    }

}
