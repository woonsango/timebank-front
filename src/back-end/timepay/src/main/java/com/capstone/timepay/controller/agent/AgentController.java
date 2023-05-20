package com.capstone.timepay.controller.agent;

import com.capstone.timepay.controller.agent.request.AgentRegisterRequest;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.service.agent.AgentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users/agents")
public class AgentController {

    private final UserRepository userRepository;
    private final AgentService agentService;

    @PostMapping("/register")
    @ApiOperation(value = "대리인 등록")
    public ResponseEntity<?> agentRegister(@RequestBody AgentRegisterRequest agentRegisterRequest, Principal principal) {
        User agent = userRepository.findByEmail(principal.getName()).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 대리인 유저입니다."));
        System.out.println(agent);
        return ResponseEntity.ok(agentService.agentRegister(agentRegisterRequest.getUid(), agent));
    }
}
