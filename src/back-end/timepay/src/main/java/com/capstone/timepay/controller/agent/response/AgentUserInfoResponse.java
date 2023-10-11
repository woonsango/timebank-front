package com.capstone.timepay.controller.agent.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AgentUserInfoResponse {
    private boolean status;
    private String agentName;
    private Long myUid;
    private String myName;
    private boolean isAccept;

}
