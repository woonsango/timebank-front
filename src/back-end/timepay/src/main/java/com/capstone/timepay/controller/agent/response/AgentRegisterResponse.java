package com.capstone.timepay.controller.agent.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AgentRegisterResponse {
    private Long uid;
    private Boolean success;
    private String content;
}
