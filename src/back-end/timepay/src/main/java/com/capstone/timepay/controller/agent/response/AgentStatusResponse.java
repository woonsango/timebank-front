package com.capstone.timepay.controller.agent.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AgentStatusResponse {
    private boolean status;
    private String content;

    public void setStatus(boolean status) { this.status = status; }
    public void setContent(String content) { this.content = content; }
}
