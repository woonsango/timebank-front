package com.capstone.timepay.controller.agent.response;

import com.capstone.timepay.domain.agent.Agent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AgentAcceptInfoResponse {
    private boolean status;

    private List<Applicant> applicant;

    private Long myUid;
    private String myName;

    public void setStatus(boolean status) { this.status = status; }
    public void setMyUid(Long myUid) { this.myUid = myUid; }
    public void setMyName(String myName) { this.myName = myName; }

}
