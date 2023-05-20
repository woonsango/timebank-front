package com.capstone.timepay.controller.agent.response;


import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AgentUserRegisterResponse {
    private Long uid;
    private Boolean status;
    private String content;
}
