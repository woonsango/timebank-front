package com.capstone.timepay.service.agent;

import com.capstone.timepay.controller.agent.response.*;
import com.capstone.timepay.domain.agent.Agent;
import com.capstone.timepay.domain.agent.AgentRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.service.user.service.UserDetailService;
import com.capstone.timepay.utility.JwtUtils;
import com.google.firebase.database.utilities.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AgentService {
    private final UserRepository userRepository;
    private final AgentRepository agentRepository;

    private final UserDetailService userDetailService;
    private final JwtUtils jwtUtils;

    public AgentUserRegisterResponse agentRegister(Long userId, User agent){
        User user = userRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 신청인 유저입니다."));

        AgentUserRegisterResponse agentUserRegisterResponse = new AgentUserRegisterResponse(
                userId, false, "알 수 없는 이유로 agentRegister 함수 실행 도중 실패했습니다.");
        if (agentRepository.findByCreatedUserAndAssignedUser(agent, user) == null) {
            agentRepository.save(Agent.builder()
                    .createdUser(agent)
                    .assignedUser(user)
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

    public AgentInfoResponse agentInfo(User user){
        Agent agentInfo = agentRepository.findFirstByCreatedUser(user);
        AgentInfoResponse agentInfoResponse = new AgentInfoResponse(false, null,
                null,null);

        if(agentInfo == null || user.getAssignedAgents() == null){
            agentInfoResponse = new AgentInfoResponse(false, null,
                    user.getUserId(),user.getName());
        }
        else{
            List<Agent> assignedUsers = agentInfo.getCreatedUser().getCreatedAgents();
            List<Applicant> applicants = new ArrayList<>();

            for (Agent agent : assignedUsers) {
                Applicant applicant = new Applicant();
                applicant.setAppliUid(agent.getAssignedUser().getUserId());
                applicant.setAppliName(agent.getAssignedUser().getName());
                applicants.add(applicant);
            }

            agentInfoResponse = new AgentInfoResponse(true, applicants,
                    user.getUserId(),user.getName());
        }

        return agentInfoResponse;
    }

    public AgentTransResponse agentTrans(User user){
        AgentTransResponse agentTransResponse = new AgentTransResponse(null, false);

        final UserDetails userDetails = userDetailService.loadUserByUsername(user.getEmail());
        final String token = jwtUtils.createToken(userDetails.getUsername(), user.getRoles());

        agentTransResponse.setToken(token); agentTransResponse.setStatus(true);

        return agentTransResponse;
    }

    public AgentStatusResponse agentDelete(User agent, User user){
        AgentStatusResponse agentStatusResponse = new AgentStatusResponse(false, "알 수 없는 이유로 함수 실행 과정에서 에러 발생");

        if(agentRepository.findByCreatedUserAndAssignedUser(agent, user) != null) {
            agentRepository.delete(agentRepository.findByCreatedUserAndAssignedUser(agent, user));
            agentStatusResponse = new AgentStatusResponse(true, "성공적으로 삭제되었습니다.");
        } else{
            agentStatusResponse = new AgentStatusResponse(false, "대리인 정보에 신청된 유저를 찾지 못했습니다.");
        }
        return agentStatusResponse;
    }
}
