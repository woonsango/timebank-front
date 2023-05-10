package com.capstone.timepay.controller.user;

import com.capstone.timepay.service.user.service.UserCheckService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/check")
public class CheckController {

    private final UserCheckService userCheckService;

    @GetMapping("/nickname/{nickName}")
    @ApiOperation(value = "닉네임 중복검사 API", notes = "닉네임을 입력받아 중복검사, boolean값을 return합니다.")
    public ResponseEntity<?> checkNickName(@PathVariable String nickName) {
        return ResponseEntity.ok(userCheckService.checkNickName(nickName));
    }
}
