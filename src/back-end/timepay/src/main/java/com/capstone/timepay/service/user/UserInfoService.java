package com.capstone.timepay.service.user;

import com.capstone.timepay.controller.user.request.RequestDTO;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.domain.userProfile.UserProfile;
import com.capstone.timepay.domain.userProfile.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


/* 받은 데이터를 데이터베이스에 저장 */
@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public void createUserInfo(RequestDTO userData){
        /* 유저 프로필 데이터 저장 */
        Long userUid = userData.getUid();


        /* uid값 비교하여 중복된 데이터는 데이터베이스에 저장X */
        if(userProfileRepository.findByUid(userUid) == null)
        {
            UserProfile userProfile = new UserProfile();
            userProfile.setImageUrl(userData.getImageUrl());
            userProfile.setIntroduction(userData.getIntroduction());
            userProfile.setUid(userUid);
            userProfile.setCreatedAt(LocalDateTime.now());
            userProfile.setUpdatedAt(LocalDateTime.now());
            userProfileRepository.save(userProfile);

        } else {
            System.out.println("\n이미 저장된 데이터래요~\n");
        }


        /* uid를 매핑하여 user table에 데이터 입력 */
        User findUser = userRepository.findByUid(userUid);

        findUser.setName(userData.getName());
        findUser.setNickname(userData.getNickName());
        findUser.setLocation(userData.getLocation());
        findUser.setPhone(userData.getPhone());
        findUser.setBirthday(userData.getBirthday());
        findUser.setCreatedAt(LocalDateTime.now());
        findUser.setUpdatedAt(LocalDateTime.now());

        UserProfile saveUserProfile = userProfileRepository.findByUid(userUid);
        findUser.setUserProfile(saveUserProfile);

        userRepository.save(findUser);
    }

    public void updateUserInfo(RequestDTO userData){
        Long userUid = userData.getUid();

        if(userProfileRepository.findByUid(userUid) == null)
        {
            System.out.println("\n존재하지 않는 유저라네요~\n");

        } else {
            UserProfile userProfile = new UserProfile();

            /* 코드 재활용을 위한 변수 생성 */
            String imageUrl = userData.getImageUrl();
            String introduction =  userData.getIntroduction();

            if(imageUrl != null)
                userProfile.setImageUrl(imageUrl);

            if(introduction != null)
                userProfile.setIntroduction(introduction);

            userProfile.setUpdatedAt(LocalDateTime.now());
            userProfileRepository.save(userProfile);
        }

        /* uid를 매핑하여 user table에 데이터 입력 */
        User findUser = userRepository.findByUid(userUid);

        /* 코드 재활용을 위한 변수 생성*/
        String name = userData.getName();
        String nickname = userData.getNickName();
        String location = userData.getLocation();
        String phone = userData.getPhone();
        String birthday = userData.getBirthday();

        /* 여기 아래부터 받은 데이터가 있는 것들만 찾습니다. */
        if(name != null)
            findUser.setName(name);

        if(nickname != null)
            findUser.setNickname(nickname);

        if(location != null)
            findUser.setLocation(location);

        if(phone != null)
            findUser.setPhone(phone);

        if(birthday != null)
            findUser.setBirthday(birthday); // 생일은 수정 못하게?

        findUser.setUpdatedAt(LocalDateTime.now());
        userRepository.save(findUser);
    }
}
