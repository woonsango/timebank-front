package com.capstone.timepay.controller.agent.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AgentTransResponse {
    private String token;
    private boolean status;

    public void setToken(String token) { this.token = token; }
    public void setStatus(boolean status) { this.status = status; }
}
