package com.capstone.timepay.service.user.service;

import com.capstone.timepay.controller.user.request.RequestDTO;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.domain.userProfile.UserProfile;
import com.capstone.timepay.domain.userProfile.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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
            if(userRepository.findByUid(userUid) != null) {

                UserProfile userProfile = new UserProfile();
                userProfile.setImageUrl(userData.getImageUrl());
                userProfile.setIntroduction(userData.getIntroduction());
                userProfile.setUid(userUid);
                userProfile.setCreatedAt(LocalDateTime.now());
                userProfile.setUpdatedAt(LocalDateTime.now());
                userProfileRepository.save(userProfile);
            } else{
                System.out.println("\n없는 유저 데이터래요~ 프로필~\n");
            }

        } else {
            System.out.println("\n이미 저장된 데이터래요~\n");
        }

        /* uid를 매핑하여 user table에 데이터 입력 */
        User findUser = userRepository.findByUid(userUid);

        /* String으로 입력받은 생년월일을 LocalDateTime으로 형변환 */
        String birthData = userData.getBirthday();
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime birthLocalDateTime = LocalDateTime.parse(birthData, format1);

        findUser.setName(userData.getName());
        findUser.setNickname(userData.getNickName());
        findUser.setLocation(userData.getLocation());
        findUser.setPhone(userData.getPhone());
        findUser.setBirthday(birthLocalDateTime);
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
            UserProfile userProfile = userProfileRepository.findByUid(userUid);

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
        /* String으로 입력받은 생년월일을 LocalDateTime으로 형변환 */
        // String birthData = userData.getBirthday();
        // DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        // LocalDateTime birthLocalDateTime = LocalDateTime.parse(birthData, format1);

        String name = userData.getName();
        String nickname = userData.getNickName();
        String location = userData.getLocation();
        String phone = userData.getPhone();


        /* 여기 아래부터 받은 데이터가 있는 것들만 찾습니다. */
        if(name != null)
            findUser.setName(name);

        if(nickname != null)
            findUser.setNickname(nickname);

        if(location != null)
            findUser.setLocation(location);

        if(phone != null)
            findUser.setPhone(phone);

        if(userData.getBirthday() != null) {
            /* String으로 입력받은 생년월일을 LocalDateTime으로 형변환 */
            String birthData = userData.getBirthday();
            DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
            LocalDateTime birthLocalDateTime = LocalDateTime.parse(birthData, format1);
            LocalDateTime birthday = birthLocalDateTime;

            findUser.setBirthday(birthday); // 생일은 수정 못하게?
        }

        findUser.setUpdatedAt(LocalDateTime.now());
        userRepository.save(findUser);
    }

    public RequestDTO getUserInfo(Long uid){
        User userData = userRepository.findByUid(uid);
        UserProfile userProfileData = userProfileRepository.findByUid(uid);
        RequestDTO requestDTO = new RequestDTO();

        /* LocalDateTime을 출력을 위한 String 형변환 */
        String birthString = userData.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        /* 유저 테이블에서 데이터 가져오기 */
        requestDTO.setUid(uid);
        requestDTO.setName(userData.getName());
        requestDTO.setBirthday(birthString);
        requestDTO.setLocation(userData.getLocation());
        requestDTO.setPhone(userData.getPhone());
        requestDTO.setNickName(userData.getNickname());

        /* 유저 프로필 테이블에서 데이터 가져오기 */
        requestDTO.setImageUrl(userProfileData.getImageUrl());
        requestDTO.setIntroduction(userProfileData.getIntroduction());
        return requestDTO;
    }

    public void deleteUserInfo(Long uid){
        User userData = userRepository.findByUid(uid); // 중복 사용 많은데 함수로 빼둘지 고민
        
        /* deleteById를 사용하지 않고 에러 메세지를 직접 커스텀 */
        if(userData != null) {
            UserProfile userProfileData = userProfileRepository.findByUid(uid);
            userRepository.delete(userData);
            userProfileRepository.delete(userProfileData);

        } else{
            System.out.println("존재하지 않는 회원이랍니다~");
        }

    }

}
