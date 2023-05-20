package com.capstone.timepay.controller.agent;

import com.capstone.timepay.controller.agent.request.AgentRegisterRequest;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.service.agent.AgentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users/agents")
public class AgentUserController {

    private final UserRepository userRepository;
    private final AgentService agentService;

    @PostMapping("/register")
    @ApiOperation(value = "대리인 등록")
    public ResponseEntity<?> agentRegister(@RequestBody AgentRegisterRequest agentRegisterRequest, Principal principal) {
        User agent = userRepository.findByEmail(principal.getName()).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 대리인 유저입니다."));
        return ResponseEntity.ok(agentService.agentRegister(agentRegisterRequest.getUid(), agent));
    }

    @GetMapping()
    @ApiOperation(value = "대리인 조회")
    public ResponseEntity<?> agentInfo(Principal principal){
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 대리인 유저입니다."));

        return ResponseEntity.ok(agentService.agentInfo(user));
    }

    @DeleteMapping()
    @ApiOperation(value = "대리인 삭제")
    public ResponseEntity<?> agentDelete(Principal principal){
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 대리인 유저입니다."));

        return ResponseEntity.ok(agentService.agentDelete(user));
    }

//    @DeleteMapping()
//    @ApiOperation(value = "ㅇㅅㅇ")
//    public ResponseEntity<?> agentDeletete(){
//        return null;
//    }
}
