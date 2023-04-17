package com.capstone.timepay.service.user.service;

import com.capstone.timepay.controller.user.request.RequestDTO;
import com.capstone.timepay.controller.user.request.UpdateRequestDTO;
import com.capstone.timepay.controller.user.response.GetResponseDTO;
import com.capstone.timepay.controller.user.response.UpdateResponseDTO;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.domain.userProfile.UserProfile;
import com.capstone.timepay.domain.userProfile.UserProfileRepository;
import com.capstone.timepay.firebase.FirebaseService;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/* 받은 데이터를 데이터베이스에 저장 */
@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final FirebaseService firebaseService;

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
        User findUser = userRepository.findByUid(userUid).orElseThrow(IllegalArgumentException::new);

        /* String으로 입력받은 생년월일을 LocalDateTime으로 형변환 */
        /* 만약 SignUpUser 테이블에 존재하지 않으면 에러 발생 */
        /* 근데 최초 카카오 로그인을 하면 SignUpUser 테이블이 자동 생성되는데 굳이 예외처리? */
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

    public UpdateResponseDTO updateUserInfo(Authentication auth, UpdateRequestDTO userData) throws IOException, FirebaseAuthException {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow(IllegalArgumentException::new);

        if(user.getUserProfile() == null)
        {
            System.out.println("\n존재하지 않는 유저라네요~\n");

        } else {
            UserProfile userProfile = userProfileRepository.findByUid(user.getUid());

            /* 코드 재활용을 위한 변수 생성 */
            String imageUrl = imageUpload(userData.getImage());
            String introduction =  userData.getIntroduction();

            if(imageUrl != null)
                userProfile.setImageUrl(imageUrl);

            if(introduction != null)
                userProfile.setIntroduction(introduction);

            userProfile.setUpdatedAt(LocalDateTime.now());
            userProfileRepository.save(userProfile);
        }

        /* uid를 매핑하여 user table에 데이터 입력 */
        // User findUser = userRepository.findByUid(userUid).orElseThrow(IllegalArgumentException::new);

        /* 코드 재활용을 위한 변수 생성*/
        /* String으로 입력받은 생년월일을 LocalDateTime으로 형변환 */
        // String birthData = userData.getBirthday();
        // DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        // LocalDateTime birthLocalDateTime = LocalDateTime.parse(birthData, format1);

        String nickName = userData.getNickName();
        String location = userData.getLocation();


        /* 여기 아래부터 받은 데이터가 있는 것들만 찾습니다. */
        if(nickName != null)
            user.setNickname(nickName);

        if(location != null)
            user.setLocation(location);

        /* 불필요한 코드라 임시 주석처리 */
        /* 추후 필요할 가능성 있을지도 */
//        if(userData.getBirthday() != null) {
//            /* String으로 입력받은 생년월일을 LocalDateTime으로 형변환 */
//            String birthData = userData.getBirthday();
//            DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
//            LocalDateTime birthLocalDateTime = LocalDateTime.parse(birthData, format1);
//            LocalDateTime birthday = birthLocalDateTime;
//
//            findUser.setBirthday(birthday); // 생일은 수정 못하게?
//        }

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        /* 아래는 return 값을 만들기 위해 데이터 정보 추출 */
        /* uid, imageUrl,  name, nickName, sex, location, introduction, age(???) */
        Long uid = user.getUid();
        String imageUrl = user.getUserProfile().getImageUrl();
        String name = user.getName();
        String sex = user.getSex();
        String introduction = user.getUserProfile().getIntroduction();
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO(uid, imageUrl, name, nickName, sex, location, introduction);
        return updateResponseDTO;
    }

    public GetResponseDTO getUserInfo(Long uid){
        User userData = userRepository.findByUid(uid).orElseThrow(IllegalArgumentException::new);
        UserProfile userProfileData = userProfileRepository.findByUid(uid);

        /* LocalDateTime을 출력을 위한 String 형변환 */
        // String birthString = userData.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        /* 유저 테이블에서 데이터 가져오기 */
        String nickName = userData.getNickname();

        /* 유저 프로필 테이블에서 데이터 가져오기 */
        String imageUrl = userProfileData.getImageUrl();
        int timePay = userProfileData.getTimepay();

        /* 생성자를 사용하여 객체 생성 */
        GetResponseDTO getResponseDTO = new GetResponseDTO(uid, imageUrl, nickName, timePay);
        return getResponseDTO;
    }

    public GetResponseDTO getMyInfo(Authentication auth){
        String userEmail = auth.getName();
        User userData = userRepository.findByEmail(userEmail).orElseThrow(IllegalArgumentException::new);

        /* 유저 테이블에서 데이터 가져오기 */
        String nickName = userData.getNickname();

        /* 유저 프로필 테이블에서 데이터 가져오기 */
        String imageUrl = userData.getUserProfile().getImageUrl();
        int timePay = userData.getUserProfile().getTimepay();

        /* 생성자를 사용하여 객체 생성 */
        GetResponseDTO getResponseDTO = new GetResponseDTO(userData.getUid(), imageUrl, nickName, timePay);
        return getResponseDTO;
    }

//    public void deleteUserInfo2(Long uid){
//        User userData = userRepository.findByUid(uid).orElseThrow(IllegalArgumentException::new); // 중복 사용 많은데 함수로 빼둘지 고민
//
//        /* deleteById를 사용하지 않고 에러 메세지를 직접 커스텀 */
//        if(userData != null) {
//            UserProfile userProfileData = userProfileRepository.findByUid(uid);
//            userRepository.delete(userData);
//            userProfileRepository.delete(userProfileData);
//
//        } else{
//            System.out.println("존재하지 않는 회원이랍니다~");
//        }
//
//    }

    public void deleteUserInfo(Authentication auth){
        String userEmail = auth.getName();
        User userData = userRepository.findByEmail(userEmail).orElseThrow(IllegalArgumentException::new);

        /* deleteById를 사용하지 않고 에러 메세지를 직접 커스텀 */
        if(userData != null) {
            UserProfile userProfileData = userProfileRepository.findByUid(userData.getUid());
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

            return imageUrl;
        } else {

            return null;
        }
    }

    public User signUpUser(Long uid){
        User user = userRepository.findByUid(uid).orElseThrow(IllegalArgumentException::new);
        user.setSignUp(true);
        userRepository.save(user);
        return user;
    }

}