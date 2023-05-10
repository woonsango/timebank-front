package com.capstone.timepay.controller.user.response;

import lombok.*;
import org.springframework.data.domain.Page;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationListResponse {
    private int totalTime;
    private Page<CertificationList> certificationListPage;
}
