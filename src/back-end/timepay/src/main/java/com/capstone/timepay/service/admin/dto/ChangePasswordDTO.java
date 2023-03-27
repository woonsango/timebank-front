package com.capstone.timepay.service.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChangePasswordDTO {
    private String currentPassword;
    private String newPassword;
    private String newPassword2;
}
