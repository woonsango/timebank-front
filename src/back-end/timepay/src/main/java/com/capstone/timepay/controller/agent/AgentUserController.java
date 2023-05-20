package com.capstone.timepay.controller.agent;

import com.capstone.timepay.controller.agent.request.AgentUidRequest;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.service.agent.AgentUserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users/applicants")
public class AgentUserController {

    private final UserRepository userRepository;
    private final AgentUserService agentUserService;


    @GetMapping()
    @ApiOperation(value = "대리인 조회")
    public ResponseEntity<?> agentInfo(Principal principal){
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 신청인 유저입니다."));

        return ResponseEntity.ok(agentUserService.agentUserInfo(user));
    }

    @DeleteMapping()
    @ApiOperation(value = "대리인 삭제")
    public ResponseEntity<?> agentDelete(Principal principal){
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 신청인 유저입니다."));

        return ResponseEntity.ok(agentUserService.agentUserDelete(user));
    }
    @GetMapping("/apply")
    @ApiOperation(value = "대리인 신청 목록 조회")
    public ResponseEntity<?> agentApplyList(Principal principal){
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 신청인 유저입니다."));

        return ResponseEntity.ok(agentUserService.agentUserInfo(user));
    }

    @PostMapping("/register")
    @ApiOperation(value = "대리인 신청 확인/거부")
    public ResponseEntity<?> agentRegister(@RequestBody AgentUidRequest agentUidRequest, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 신청인 유저입니다."));
        return ResponseEntity.ok(agentUserService.agentUserRegister(agentUidRequest.getUid(), user));
    }

}
