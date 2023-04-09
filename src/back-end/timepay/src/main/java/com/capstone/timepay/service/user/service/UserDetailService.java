package com.capstone.timepay.service.user.service;

import com.capstone.timepay.domain.user.User;
// import org.springframework.security.core.userdetails.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.service.user.service.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        return new UserDetailsImpl(user);
    }

    public User getUser(String name) {
        return userRepository.findByName(name).get();
    }
}
