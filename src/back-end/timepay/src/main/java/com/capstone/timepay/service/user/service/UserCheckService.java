package com.capstone.timepay.service.user.service;


import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.domain.userProfile.UserProfileRepository;
import com.capstone.timepay.firebase.FirebaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCheckService {
    private final UserRepository userRepository;

    public boolean checkNickName(String nickName) {
        User user = userRepository.findByNickname(nickName).orElse(null);
        if (user == null)
            return true;
        else
            return false;

    }

}
