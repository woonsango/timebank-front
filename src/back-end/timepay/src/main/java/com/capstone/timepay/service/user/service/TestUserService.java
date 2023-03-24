package com.capstone.timepay.service.user.service;

import com.capstone.timepay.domain.user.TestUser;
import com.capstone.timepay.domain.user.TestUserRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.service.user.dto.RegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestUserService {

    private final TestUserRepository testUserRepository;

    public TestUser register(RegisterDTO registerDTO) {
        TestUser user = new TestUser();
        user.setName(registerDTO.getName());
        user.setPassword(registerDTO.getPassword());
        user.setUsername(registerDTO.getUsername());
        user.setWarning(registerDTO.isWarning());
        return testUserRepository.save(user);
    }

    public List<TestUser> findAll() {
        return testUserRepository.findAll();
    }

    public TestUser findUser(Long id) {
        return testUserRepository.findById(id).orElseThrow(()-> {
            return new IllegalArgumentException("User ID를 찾을 수 없습니다.");
        });
    }
}