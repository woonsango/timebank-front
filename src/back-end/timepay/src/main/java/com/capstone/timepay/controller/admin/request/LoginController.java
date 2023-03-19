package com.capstone.timepay.controller.admin.request;

import com.capstone.timepay.model.AuthenticationRequest;
import com.capstone.timepay.model.AuthenticationResponse;
import com.capstone.timepay.service.admin.dto.AdminDetailService;
import com.capstone.timepay.utility.JwtUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admins/")
public class LoginController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AdminDetailService adminDetailService;

    @PostMapping("/login")
    @ApiOperation(value = "로그인 API")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getAdminName(), authenticationRequest.getPassword());
        final UserDetails userDetails = adminDetailService.loadUserByUsername(authenticationRequest.getAdminName());
        final String token = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃 API")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // 현재 인증된 사용자의 인증 토큰을 가져온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 토큰이 존재하면 로그아웃 처리를 한다.
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return ResponseEntity.ok("로그아웃되었습니다.");
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
