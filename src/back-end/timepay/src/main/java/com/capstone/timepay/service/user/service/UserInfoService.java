package com.capstone.timepay.service.user.service;

import com.capstone.timepay.controller.user.request.RequestDTO;
import com.capstone.timepay.controller.user.response.ResponseDTO;
import com.capstone.timepay.domain.signUpUser.SignUpUser;
import com.capstone.timepay.domain.signUpUser.SignUpUserRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.domain.userProfile.UserProfile;
import com.capstone.timepay.domain.userProfile.UserProfileRepository;
import com.capstone.timepay.firebase.FirebaseService;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


/* 받은 데이터를 데이터베이스에 저장 */
@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final FirebaseService firebaseService;
    private final KakaoLoginService kakaoLoginService;
    private final SignUpUserRepository signUpUserRepository;

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
        SignUpUser findUser = signUpUserRepository.findByUid(userUid).orElse(null);

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

        signUpUserRepository.save(findUser);
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
        User findUser = userRepository.findByUid(userUid).orElse(null);

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

    public ResponseDTO getUserInfo(Long uid){
        User userData = userRepository.findByUid(uid).orElse(null);
        UserProfile userProfileData = userProfileRepository.findByUid(uid);

        /* LocalDateTime을 출력을 위한 String 형변환 */
        // String birthString = userData.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        /* 유저 테이블에서 데이터 가져오기 */
        String nickName = userData.getNickname();

        /* 유저 프로필 테이블에서 데이터 가져오기 */
        String imageUrl = userProfileData.getImageUrl();
        int timePay = userProfileData.getTimepay();

        /* 생성자를 사용하여 객체 생성 */
        ResponseDTO responseDTO = new ResponseDTO(uid, nickName, imageUrl, timePay);
        return responseDTO;
    }

    public void deleteUserInfo(Long uid){
        User userData = userRepository.findByUid(uid).orElse(null); // 중복 사용 많은데 함수로 빼둘지 고민
        
        /* deleteById를 사용하지 않고 에러 메세지를 직접 커스텀 */
        if(userData != null) {
            UserProfile userProfileData = userProfileRepository.findByUid(uid);
            userRepository.delete(userData);
            userProfileRepository.delete(userProfileData);

        } else{
            System.out.println("존재하지 않는 회원이랍니다~");
        }

    }

    public String imageUpload(MultipartFile image) throws IOException, FirebaseAuthException {
        if(image.getSize() != 0) {
            // 이미지를 업로드하고 해당 url 저장
            String imageUrl = firebaseService.uploadFiles(image);
            System.out.println(imageUrl);

            // 위의 url 주소를 저장할 테이블의 객체를 생성할 때 사용
            // ex ) UserProfile profile = UserProfile.builder().imageUrl(imageUrl). ........  . build();

            // url을 포함한 객체를 생성하고 해당 객체를 repository를 통해 테이블에 저장
            // ex ) userProfileRepository.save(profile);
            return imageUrl;
        } else {
            return null;
        }
    }

    public User signUpUser(Long uid){
        SignUpUser signupUser = signUpUserRepository.findByUid(uid).orElse(null);
        User user = kakaoLoginService.converSignUpToUser(signupUser);

        userRepository.save(user);
        signUpUserRepository.delete(signupUser);
        return user;
    }

}
