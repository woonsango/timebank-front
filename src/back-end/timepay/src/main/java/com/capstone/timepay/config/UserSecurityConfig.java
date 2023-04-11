package com.capstone.timepay.config;

import com.capstone.timepay.filter.UserJwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(1)
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private UserDetailService userDetailService;

    @Autowired
    private UserJwtFilter userJwtFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/users/create").permitAll() // 회원 가입
                .antMatchers("/api/users/get/**").permitAll() // 회원 조회는 인증없이 사용 가능
                .antMatchers(HttpMethod.GET, "/api/users/**").authenticated() // 그 외 모든 api는 jwt 인증 필요
                .antMatchers(HttpMethod.POST, "/api/notifications/**").authenticated()
                .anyRequest().permitAll().and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilterBefore(userJwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
