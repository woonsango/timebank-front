package com.capstone.timepay.filter;

import com.capstone.timepay.service.admin.AdminDetailService;
import com.capstone.timepay.service.user.service.UserDetailService;
import com.capstone.timepay.utility.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class AdminJwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AdminDetailService adminDetailService;
    private final UserDetailService userDetailService;
    private final List<String> role_admin = new ArrayList<>(Collections.singletonList("ROLE_ADMIN"));
    private final List<String> role_user = new ArrayList<>(Collections.singletonList("ROLE_USER"));

    @Autowired
    public AdminJwtFilter(JwtUtils jwtUtils, AdminDetailService adminDetailService, UserDetailService userDetailService) {
        this.jwtUtils = jwtUtils;
        this.adminDetailService = adminDetailService;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        UserDetails userDetails = null;

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.replace("Bearer ", "");

            try {
                Claims claims = Jwts.parser().setSigningKey(jwtUtils.getSecretKey()).parseClaimsJws(token).getBody();
                String username = claims.getSubject();

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    if (claims.get("roles").equals(role_admin)) {
                        userDetails = adminDetailService.loadUserByUsername(username);

                    } else if (claims.get("roles").equals(role_user)) {
                        userDetails =  userDetailService.loadUserByUsername(username);
                    }

                    if (jwtUtils.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (JwtException e) {
                logger.error("JWT validation error");
            }
        }
        filterChain.doFilter(request, response);
    }
}
