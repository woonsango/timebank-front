package com.capstone.timepay.controller.admin;

import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.domain.admin.AdminRepository;
import com.capstone.timepay.model.AuthenticationRequest;
import com.capstone.timepay.model.AuthenticationResponse;
import com.capstone.timepay.service.admin.AdminDetailService;
import com.capstone.timepay.utility.JwtUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AdminDetailService adminDetailService;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    @ApiOperation(value = "로그인 API")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        String adminName = authenticationRequest.getAdminName();
        Admin adminData = adminRepository.findByAdminName(adminName).orElse(null);
        if (!passwordEncoder.matches(authenticationRequest.getPassword(), adminData.getPassword())) {
            return ResponseEntity.ok("Password invalid");
        }
        final UserDetails userDetails = adminDetailService.loadUserByUsername(adminName);
        // final String token = jwtUtils.generateToken(userDetails, adminData.getRoles());
        final String token = jwtUtils.createToken(userDetails.getUsername(), adminData.getRoles());
        final Admin admin = adminDetailService.getAdmin(adminName);
        return ResponseEntity.ok(new AuthenticationResponse(token, admin));
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

}
