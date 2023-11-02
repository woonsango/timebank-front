package com.capstone.timepay.service.agent;

import com.capstone.timepay.controller.agent.request.AgentApplyRequest;
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
                if (agent.isAccept()) {
                    Applicant applicant = new Applicant();
                    applicant.setAppliUid(agent.getAssignedUser().getUserId());
                    applicant.setAppliName(agent.getAssignedUser().getName());
                    applicants.add(applicant);
                }
            }

            agentInfoResponse = new AgentInfoResponse(true, applicants,
                    user.getUserId(), user.getName());
        }

        return agentInfoResponse;
    }

    /* 테스트 필요 */
    public AgentAcceptInfoResponse agentAcceptList(User user){
        AgentAcceptInfoResponse agentAcceptInfoResponse = new AgentAcceptInfoResponse(false, null,
                null,null);

        if(agentRepository.findFirstByCreatedUser(user) == null){
            return agentAcceptInfoResponse;
        }

        Agent agentInfo = agentRepository.findFirstByCreatedUser(user);
        
        /* isAccept가 false 리스트 */
        List<Agent> assignedUsers = agentInfo.getCreatedUser().getCreatedAgents();
        List<Agent> waitingAgents = assignedUsers.stream()
                .filter(agent -> !agent.isAccept())
                .collect(Collectors.toList());

        List<Applicant> applicants = new ArrayList<>();

        for (Agent agent : waitingAgents) {
           {
               Applicant applicant = new Applicant();
               applicant.setAppliUid(agent.getAssignedUser().getUserId());
               applicant.setAppliName(agent.getAssignedUser().getName());
               applicants.add(applicant);
            }
        }
        agentAcceptInfoResponse = new AgentAcceptInfoResponse(true, applicants,
                user.getUserId(), user.getName());

        return agentAcceptInfoResponse;
    }

    public AgentStatusResponse agentApply(AgentApplyRequest agentApplyRequest, User agent){
        AgentStatusResponse agentStatusResponse = new AgentStatusResponse(false,
                "알 수 없는 이유로 함수 실행 과정에서 에러 발생");

        User user = userRepository.findById(agentApplyRequest.getUid()).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 대리인 유저입니다."));

        if(agentRepository.findByCreatedUserAndAssignedUser(agent, user) != null) {
            Agent agentInfo = agentRepository.findByCreatedUserAndAssignedUser(agent, user);

            if(!agentInfo.isAccept()) {
                if (agentApplyRequest.isApply()) { // true
                    agentInfo.setAccept(true);
                    agentRepository.save(agentInfo);
                    agentStatusResponse = new AgentStatusResponse(true,
                            "성공적으로 수락하였습니다.");
                }
                else {
                    agentInfo.setAccept(false);
                    agentRepository.save(agentInfo);
                    agentStatusResponse = new AgentStatusResponse(true,
                            "성공적으로 거절하였습니다.");
                }
            }
            else{
                agentStatusResponse = new AgentStatusResponse(false,
                        "이미 수락한 신청인입니다.");
            }
        }
        else{
            agentStatusResponse = new AgentStatusResponse(false,
                    "신청되어 있지 않는 유저입니다.");
        }

        return agentStatusResponse;
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
