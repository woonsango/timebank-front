package com.capstone.timepay.controller.organization;

import com.capstone.timepay.controller.organization.request.OrgaUserLoginRequest;
import com.capstone.timepay.controller.organization.response.AuthenticationResponse;
import com.capstone.timepay.domain.organization.Organization;
import com.capstone.timepay.domain.organization.OrganizationRepository;
import com.capstone.timepay.service.organization.OrganizationDetailService;
import com.capstone.timepay.utility.JwtUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/organizations/")
public class OrgaUserLoginController {

    private final OrganizationRepository organizationRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private OrganizationDetailService organizationDetailService;

    @PostMapping("/login")
    @ApiOperation(value = "기관 회원 로그인 API")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody OrgaUserLoginRequest request) throws Exception {

        Organization organization = organizationRepository.findByAccount(request.getId()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
        final UserDetails userDetails = organizationDetailService.loadUserByUsername(request.getId());
        // final String token = jwtUtils.generateToken(userDetails, adminData.getRoles());
        final String token = jwtUtils.createToken(userDetails.getUsername(), organization.getRoles());
        final Organization target = organizationDetailService.getOrganization(request.getId());
        return ResponseEntity.ok(new AuthenticationResponse(token, target));
    }

    @PostMapping("/logout")
    @ApiOperation(value = "기관 회원 로그아웃 API")
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
