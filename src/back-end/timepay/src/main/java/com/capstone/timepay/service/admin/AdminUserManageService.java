package com.capstone.timepay.service.admin;

import com.capstone.timepay.controller.admin.response.MainResponse;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.domain.userProfile.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserManageService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public List<MainResponse> showAllUserList() {

        List<User> users = userRepository.findAll();

        return users.stream()
                    .map(user -> MainResponse.builder()
                        .userId(user.getUserId())
                        .userName(user.getName())
                        .nickName(user.getNickname())
                        .sex(user.getSex())
                        .birth(user.getBirthday())
                        .region(user.getLocation())
                        .timepay(0) // 수정 필요
                        .build())
                    .sorted(Comparator.comparing(MainResponse::getUserId))
                    .collect(Collectors.toList());
    }
}
