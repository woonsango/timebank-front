package com.capstone.timepay.service.user.service;

import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }

        return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(), user.get().getEncodedPassword(),
                new ArrayList<>());

        //return new UserDetailsImpl(user);
    }

    public User getUser(String name) {
        return userRepository.findByName(name).get();
    }
}
