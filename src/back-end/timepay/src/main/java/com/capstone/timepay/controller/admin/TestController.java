package com.capstone.timepay.controller.admin;

import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.domain.user.model.AuthenticationResponse;
import com.capstone.timepay.service.user.service.UserDetailService;
import com.capstone.timepay.utility.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/testuser")
public class TestController {

    private final UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailService userDetailService;

    @GetMapping()
    public ResponseEntity<?> getTestToken() {
        Optional<User> user = userRepository.findByName("TEST-USER");
        if (!user.isPresent()) {
            User testUser = User.builder()
                    .name("TEST-USER")
                    .sex("M")
                    .birthday(LocalDateTime.of(1990, 1, 1, 0, 0))
                    .location("San Francisco")
                    .phone("123-456-7890")
                    .nickname("johndoe")
                    .email("johndoe@example.com")
                    .encodedPassword("encodedPassword123")
                    .roles(Collections.singletonList("ROLE_USER"))
                    .isBanned(false)
                    .isSignUp(true)
                    .build();

            user = Optional.ofNullable(testUser);
            userRepository.save(testUser);
        }

        final UserDetails userDetails = userDetailService.loadUserByUsername(user.get().getEmail());
        final String token = jwtUtils.createToken(userDetails.getUsername(), user.get().getRoles());
        return ResponseEntity.ok(new AuthenticationResponse(token, user.get().getRoles(), true));
    }
}
