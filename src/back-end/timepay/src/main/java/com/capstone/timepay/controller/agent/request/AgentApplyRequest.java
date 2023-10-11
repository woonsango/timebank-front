package com.capstone.timepay.controller.agent.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AgentApplyRequest {
    private Long uid;
    private boolean apply;

    public void setUid(Long uid) { this.uid = uid; }
    public void setApply(boolean apply) { this.apply = apply; }
}
