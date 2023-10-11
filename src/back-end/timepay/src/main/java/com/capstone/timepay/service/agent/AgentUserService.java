package com.capstone.timepay.service.agent;

import com.capstone.timepay.controller.agent.response.AgentStatusResponse;
import com.capstone.timepay.controller.agent.response.AgentUserInfoResponse;
import com.capstone.timepay.controller.agent.response.AgentUserRegisterResponse;
import com.capstone.timepay.domain.agent.Agent;
import com.capstone.timepay.domain.agent.AgentRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AgentUserService {

    private final UserRepository userRepository;
    private final AgentRepository agentRepository;

    public AgentUserRegisterResponse agentUserRegister(User agent, User user){
        AgentUserRegisterResponse agentUserRegisterResponse = new AgentUserRegisterResponse(
                agent.getUserId(), false, "알 수 없는 이유로 agentRegister 함수 실행 도중 실패했습니다.");
        if (agentRepository.findByCreatedUserAndAssignedUser(agent, user) == null) {
            agentRepository.save(Agent.builder()
                    .createdUser(agent)
                    .assignedUser(user)
                    .isAccept(false)
                    .build()
            );
            agentUserRegisterResponse.setStatus(true);
            agentUserRegisterResponse.setContent("등록에 성공했습니다.");

        }
        else{
            agentUserRegisterResponse.setStatus(false);
            agentUserRegisterResponse.setContent("이미 등록된 대리인과 신청인입니다.");
        }
        return agentUserRegisterResponse;
    }

    public AgentUserInfoResponse agentUserInfo(User user){
        Agent agentInfo = agentRepository.findByAssignedUser(user);
        AgentUserInfoResponse agentUserInfoResponse = new AgentUserInfoResponse(false, null,
                null,null, false);

        if(agentInfo == null || agentInfo.getCreatedUser() == null){
            agentUserInfoResponse = new AgentUserInfoResponse(false, null,
                    user.getUserId(),user.getName(), false);
        }
        else{
            agentUserInfoResponse = new AgentUserInfoResponse(true, agentInfo.getCreatedUser().getName(),
                    user.getUserId(),user.getName(), agentInfo.isAccept());
        }

        return agentUserInfoResponse;
    }

    public AgentStatusResponse agentUserDelete(User user){
        AgentStatusResponse agentStatusResponse = new AgentStatusResponse(false, "알 수 없는 이유로 함수 실행 과정에서 에러 발생");
        if(agentRepository.findByAssignedUser(user) != null) {
            agentRepository.delete(agentRepository.findByAssignedUser(user));
            agentStatusResponse = new AgentStatusResponse(true, "성공적으로 삭제되었습니다.");
        } else{
            agentStatusResponse = new AgentStatusResponse(false, "등록된 대리인이 없습니다.");
        }
        return agentStatusResponse;
    }
}
