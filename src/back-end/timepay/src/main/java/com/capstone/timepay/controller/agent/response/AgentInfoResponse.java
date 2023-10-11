package com.capstone.timepay.controller.agent.response;

import com.capstone.timepay.domain.agent.Agent;
import com.capstone.timepay.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AgentInfoResponse {
    private boolean status;
    private List<Applicant> applicant;

    private Long myUid;
    private String myName;

    public void setStatus(boolean status) { this.status = status; }
    public void setMyUid(Long myUid) { this.myUid = myUid; }
    public void setMyName(String myName) { this.myName = myName; }

}
