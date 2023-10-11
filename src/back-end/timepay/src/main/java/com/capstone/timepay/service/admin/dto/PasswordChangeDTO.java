package com.capstone.timepay.service.admin.dto;

import lombok.*;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeDTO {

    private String currentPassword;
    private String newPassword;
    private String newPassword2;

}
