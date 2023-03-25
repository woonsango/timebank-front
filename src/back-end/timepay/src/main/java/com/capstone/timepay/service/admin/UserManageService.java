package com.capstone.timepay.service.admin;

import com.capstone.timepay.controller.admin.response.userManage.MainResponse;
import com.capstone.timepay.controller.admin.response.userManage.UserProfileResponse;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.domain.userProfile.UserProfileRepository;
import com.capstone.timepay.service.admin.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserManageService {

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
                        .timepay(user.getUserProfile().getTimepay()) // 확인 필요
                        .build())
                    .sorted(Comparator.comparing(MainResponse::getUserId))
                    .collect(Collectors.toList());
    }

    public List<MainResponse> showAllUserListByName(Long userId, String value) {

        List<User> users = new ArrayList<>();

        if(!ObjectUtils.isEmpty(userId) && !ObjectUtils.isEmpty(value)){
            User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
            if(!user.getName().equals(value))
                throw new IllegalArgumentException("존재하지 않는 회원입니다.");
            users.add(user);
        }
        else if(!ObjectUtils.isEmpty(userId)){
            User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
            users.add(user);
        }
        else if(!ObjectUtils.isEmpty(value)){
            users = userRepository.findAllByName(value).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
        }
        else{
            users = userRepository.findAll();
        }

        return users.stream()
                .map(user -> MainResponse.builder()
                        .userId(user.getUserId())
                        .userName(user.getName())
                        .nickName(user.getNickname())
                        .sex(user.getSex())
                        .birth(user.getBirthday())
                        .region(user.getLocation())
                        .timepay(user.getUserProfile().getTimepay()) // 확인 필요
                        .build())
                .sorted(Comparator.comparing(MainResponse::getUserId))
                .collect(Collectors.toList());
    }
    public List<MainResponse> showAllUserListByEmail(Long userId, String query) {

        List<User> users = new ArrayList<>();

        if(!ObjectUtils.isEmpty(userId) && !ObjectUtils.isEmpty(query)){
            User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
            if(!user.getEmail().equals(query))
                throw new IllegalArgumentException("존재하지 않는 회원입니다.");
            users.add(user);
        }
        else if(!ObjectUtils.isEmpty(userId)){
            User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
            users.add(user);
        }
        else if(!ObjectUtils.isEmpty(query)){
            users = userRepository.findAllByEmail(query).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
        }
        else{
            users = userRepository.findAll();
        }

        return users.stream()
                .map(user -> MainResponse.builder()
                        .userId(user.getUserId())
                        .userName(user.getName())
                        .nickName(user.getNickname())
                        .sex(user.getSex())
                        .birth(user.getBirthday())
                        .region(user.getLocation())
                        .timepay(user.getUserProfile().getTimepay()) // 확인 필요
                        .build())
                .sorted(Comparator.comparing(MainResponse::getUserId))
                .collect(Collectors.toList());
    }
    public List<MainResponse> showAllUserListByNickname(Long userId, String query) {

        List<User> users = new ArrayList<>();

        if(!ObjectUtils.isEmpty(userId) && !ObjectUtils.isEmpty(query)){
            User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
            if(!user.getNickname().equals(query))
                throw new IllegalArgumentException("존재하지 않는 회원입니다.");
            users.add(user);
        }
        else if(!ObjectUtils.isEmpty(userId)){
            User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
            users.add(user);
        }
        else if(!ObjectUtils.isEmpty(query)){
            users = userRepository.findAllByNickname(query).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
        }
        else{
            users = userRepository.findAll();
        }

        return users.stream()
                .map(user -> MainResponse.builder()
                        .userId(user.getUserId())
                        .userName(user.getName())
                        .nickName(user.getNickname())
                        .sex(user.getSex())
                        .birth(user.getBirthday())
                        .region(user.getLocation())
                        .timepay(user.getUserProfile().getTimepay()) // 확인 필요
                        .build())
                .sorted(Comparator.comparing(MainResponse::getUserId))
                .collect(Collectors.toList());
    }

    public void updateUserInfo(UserUpdateDto userInfo) {

        User user = userRepository.findById(userInfo.getUserId()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));
        user.updateNickname(userInfo.getNickname());
        user.updateBirth(userInfo.getBirth());
        user.updateName(userInfo.getName());
        user.updateLocation(userInfo.getRegion());

        log.info(user.getName());
        log.info(user.getLocation());
        log.info(user.getNickname());
        log.info(user.getBirthday().toString());

        userRepository.save(user);

    }

    public UserProfileResponse showUserProfile(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 회원입니다."));

        return new UserProfileResponse(user.getUserProfile().getImageUrl());

    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
