package com.capstone.timepay.service.agent;

import com.capstone.timepay.controller.agent.response.AgentRegisterResponse;
import com.capstone.timepay.domain.agent.Agent;
import com.capstone.timepay.domain.agent.AgentRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AgentService {

    private final UserRepository userRepository;
    private final AgentRepository agentRepository;

    public AgentRegisterResponse agentRegister(Long userId, User agent){
        User user = userRepository.findById(userId).orElseThrow(()
        -> new IllegalArgumentException("존재하지 않는 신청인 유저입니다."));
        agentRepository.save(Agent.builder()
                .agent_user(agent)
                .user(user)
                .build()
        );
        AgentRegisterResponse agentRegisterResponse = new AgentRegisterResponse(
                userId, true);
        return agentRegisterResponse;
    }
}
