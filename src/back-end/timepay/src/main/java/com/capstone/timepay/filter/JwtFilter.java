package com.capstone.timepay.filter;

import com.capstone.timepay.service.admin.AdminDetailService;
import com.capstone.timepay.service.organization.OrganizationDetailService;
import com.capstone.timepay.service.user.service.UserDetailService;
import com.capstone.timepay.utility.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AdminDetailService adminDetailService;
    private final UserDetailService userDetailService;
    private final OrganizationDetailService organizationDetailService;
    private final List<String> role_admin = new ArrayList<>(Collections.singletonList("ROLE_ADMIN"));
    private final List<String> role_user = new ArrayList<>(Collections.singletonList("ROLE_USER"));
    private final List<String> role_organization = new ArrayList<>(Collections.singletonList("ROLE_ORGANIZATION"));

    @Autowired
    public JwtFilter(JwtUtils jwtUtils, AdminDetailService adminDetailService, UserDetailService userDetailService, OrganizationDetailService organizationDetailService) {
        this.jwtUtils = jwtUtils;
        this.adminDetailService = adminDetailService;
        this.userDetailService = userDetailService;
        this.organizationDetailService = organizationDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        UserDetails userDetails = null;

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            logger.debug("OPTIONS METHOD CALLED!");
        }

        else if (header != null && header.startsWith("Bearer ")) {
            String token = header.replace("Bearer ", "");

            try {
                Claims claims = Jwts.parser().setSigningKey(jwtUtils.getSecretKey()).parseClaimsJws(token).getBody();
                //System.out.println(Jwts.parserBuilder().setSigningKey(jwtUtils.getSecretKey()).build().parseClaimsJws(token).getBody());
                String username = claims.getSubject();

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (claims.get("roles").equals(role_admin)) {
                        userDetails = adminDetailService.loadUserByUsername(username);

                    } else if (claims.get("roles").equals(role_organization)) {
                        userDetails =  organizationDetailService.loadUserByUsername(username);

                    } else if (claims.get("roles").equals(role_user)) {
                        userDetails =  userDetailService.loadUserByUsername(username);
                    }


                    if (jwtUtils.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
                    }
                }
            } catch (JwtException e) {
                logger.error("JWT validation error");
            }
        }
        filterChain.doFilter(request, response);
    }
}
