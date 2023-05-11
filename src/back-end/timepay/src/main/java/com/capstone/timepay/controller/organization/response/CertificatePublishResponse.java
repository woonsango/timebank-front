package com.capstone.timepay.controller.organization.response;

import lombok.*;
import org.springframework.data.domain.Page;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificatePublishResponse {

    private VolunteerInfo volunteerInfo;
    private Page<ParticipateUsers> participateUsers;

}
