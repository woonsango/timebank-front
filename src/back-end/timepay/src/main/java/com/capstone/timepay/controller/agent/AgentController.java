package com.capstone.timepay.controller.agent;

import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.service.agent.AgentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users/agents")
public class AgentController {
    private final UserRepository userRepository;
    private final AgentService agentService;

    @GetMapping()
    @ApiOperation(value = "신청인 조회")
    public ResponseEntity<?> agentInfo(Principal principal){
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 대리인 유저입니다."));

        return ResponseEntity.ok(agentService.agentInfo(user));
    }
}
