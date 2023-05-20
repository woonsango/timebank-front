package com.capstone.timepay.controller.agent;

import com.capstone.timepay.controller.agent.request.AgentUidRequest;
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
public class AgentController {
    private final UserRepository userRepository;
    private final AgentService agentService;




    @PostMapping("/trans")
    @ApiOperation(value = "신청인 계정 전환")
    public ResponseEntity<?> agentTrans(@RequestBody AgentUidRequest agentUidRequest, Principal principal) {
        User user = userRepository.findById(agentUidRequest.getUid()).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 대리인 유저입니다."));

        return ResponseEntity.ok(agentService.agentTrans(user));
    }

    @DeleteMapping()
    @ApiOperation(value = "신청인 삭제")
    public ResponseEntity<?> agentDelete(@RequestBody AgentUidRequest agentUidRequest, Principal principal){
        User agent = userRepository.findByEmail(principal.getName()).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 대리인 유저입니다."));
        User user = userRepository.findById(agentUidRequest.getUid()).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 대리인 유저입니다."));

        return ResponseEntity.ok(agentService.agentDelete(agent, user));
    }

    @GetMapping()
    @ApiOperation(value = "신청인 조회")
    public ResponseEntity<?> agentInfo(Principal principal){
        User agent = userRepository.findByEmail(principal.getName()).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 대리인 유저입니다."));

        return ResponseEntity.ok(agentService.agentInfo(agent));
    }

    /* 아래 2개 개발 필요 (미완성) */
    @GetMapping("/waiting")
    @ApiOperation(value = "대리인 신청 목록 조회")
    public ResponseEntity<?> agentRegisterList(Principal principal) {
        User agent = userRepository.findByEmail(principal.getName()).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 대리인 유저입니다."));
        return ResponseEntity.ok(agentService.agentAcceptList(agent));
    }

//    @PostMapping("/register")
//    @ApiOperation(value = "대리인 신청 확인/거부")
//    public ResponseEntity<?> agentRegister(@RequestBody AgentUidRequest agentUidRequest, Principal principal) {
//        User agent = userRepository.findByEmail(principal.getName()).orElseThrow(()
//                -> new IllegalArgumentException("존재하지 않는 대리인 유저입니다."));
//        return ResponseEntity.ok(agentService.agentRegister(agentUidRequest.getUid(), agent));
//    }
}
