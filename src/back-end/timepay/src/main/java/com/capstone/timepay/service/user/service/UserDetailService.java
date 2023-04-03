package com.capstone.timepay.service.user.service;

import com.capstone.timepay.domain.user.User;
// import org.springframework.security.core.userdetails.User;
import com.capstone.timepay.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<com.capstone.timepay.domain.user.User> user = userRepository.findByName(name);
        //Optional<User> user = userRepository.findByName(name);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + name);
        }

        return new org.springframework.security.core.userdetails.User(
                user.get().getName(),user.get().getNickname(),
                new ArrayList<>());
    }

    public User getUser(String name) {
        return userRepository.findByName(name).get();
    }
}
