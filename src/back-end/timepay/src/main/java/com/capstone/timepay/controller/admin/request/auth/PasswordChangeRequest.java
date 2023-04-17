package com.capstone.timepay.controller.admin.request.auth;

import com.capstone.timepay.service.admin.dto.PasswordChangeDTO;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeRequest {

    @NotNull
    private String currentPassword;

    @NotNull
    private String newPassword;

    @NotNull
    private String newPassword2;

    public PasswordChangeDTO toServiceDto() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, PasswordChangeDTO.class);
    }
}
