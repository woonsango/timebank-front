package com.capstone.timepay.service.user.service;

import com.capstone.timepay.controller.user.request.RequestDTO;
import com.capstone.timepay.controller.user.request.UpdateRequestDTO;
import com.capstone.timepay.controller.user.response.GetResponseDTO;
import com.capstone.timepay.controller.user.response.UpdateResponseDTO;
import com.capstone.timepay.domain.board.Board;
import com.capstone.timepay.domain.board.BoardRepository;
import com.capstone.timepay.domain.comment.Comment;
import com.capstone.timepay.domain.comment.CommentRepository;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.freeBoard.FreeBoardRepository;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import com.capstone.timepay.domain.freeRegister.FreeRegister;
import com.capstone.timepay.domain.freeRegister.FreeRegisterRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.domain.userProfile.UserProfile;
import com.capstone.timepay.domain.userProfile.UserProfileRepository;
import com.capstone.timepay.firebase.FirebaseService;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/* 받은 데이터를 데이터베이스에 저장 */
@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final FirebaseService firebaseService;
    private final FreeBoardRepository freeBoardRepository;
    private final FreeRegisterRepository freeRegisterRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void createUserInfo(RequestDTO userData){
        /* 유저 프로필 데이터 저장 */
        Long uid = Long.parseLong(userData.getId());
        User user = userRepository.findById(uid).orElseThrow(()->new IllegalArgumentException("존재하지 않는 유저입니다."));


        /* uid값 비교하여 중복된 데이터는 데이터베이스에 저장X */
        if(user.getUserProfile() == null)
        {

            UserProfile userProfile = new UserProfile();
            userProfile.setImageUrl(userData.getImageUrl());
            userProfile.setIntroduction(userData.getIntroduction());
            userProfile.setCreatedAt(LocalDateTime.now());
            userProfile.setUpdatedAt(LocalDateTime.now());
            user.setUserProfile(userProfileRepository.save(userProfile));

        } else {
            System.out.println("\n이미 저장된 데이터래요~\n");
        }

        /* String으로 입력받은 생년월일을 LocalDateTime으로 형변환 */
        /* 만약 SignUpUser 테이블에 존재하지 않으면 에러 발생 */
        /* 근데 최초 카카오 로그인을 하면 SignUpUser 테이블이 자동 생성되는데 굳이 예외처리? */
        String birthData = userData.getBirthday();
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime birthLocalDateTime = LocalDateTime.parse(birthData, format1);

        user.setName(userData.getName());
        user.setNickname(userData.getNickName());
        user.setLocation(userData.getLocation());
        user.setPhone(userData.getPhone());
        user.setBirthday(birthLocalDateTime);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        user.setDeviceToken(userData.getDeviceToken()); // firebase device token

        userRepository.save(user);
    }

    @Transactional
    public UpdateResponseDTO updateUserInfo(Authentication auth, UpdateRequestDTO userData, MultipartFile image) throws IOException, FirebaseAuthException {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 유저입니다."));

        if(user.getUserProfile() == null)
        {
            System.out.println("\n존재하지 않는 유저라네요~\n");

        } else {
            UserProfile userProfile = user.getUserProfile();

            /* 코드 재활용을 위한 변수 생성 */
            String imageUrl = imageUpload(image);
            String introduction =  userData.getIntroduction();
            String location = userData.getLocation();

            if(imageUrl != null)
                userProfile.setImageUrl(imageUrl);

            if(introduction != null)
                userProfile.setIntroduction(introduction);

            if(location != null)
                user.setLocation(location);

            userProfile.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
            userProfileRepository.save(userProfile);
        }


        String nickName = userData.getNickName();
        String location = userData.getLocation();


        /* 여기 아래부터 받은 데이터가 있는 것들만 찾습니다. */
        if(nickName != null)
            user.setNickname(nickName);

        if(location != null)
            user.setLocation(location);


        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        /* 아래는 return 값을 만들기 위해 데이터 정보 추출 */
        /* uid, imageUrl,  name, nickName, sex, location, introduction, age(???) */
        Long id = user.getUserId();
        String imageUrl = user.getUserProfile().getImageUrl();
        String name = user.getName();
        String sex = user.getSex();
        String introduction = user.getUserProfile().getIntroduction();
        UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO(id, imageUrl, name, nickName, sex, location, introduction);
        return updateResponseDTO;
    }

    @Transactional(readOnly = true)
    public GetResponseDTO getUserInfo(Long id, int pageIndex, int pageSize){
        User userData = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 유저입니다."));
        UserProfile userProfileData = userData.getUserProfile();
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        /* 유저 테이블에서 데이터 가져오기 */
        String nickName = userData.getNickname();
        String location = userData.getLocation();
        Page<Board> boards = boardRepository.findAll(pageable);
        Page<Comment> comments = commentRepository.findAll(pageable);


        List<FreeRegister> freeRegisters = userData.getFreeRegisters();
        List<DealRegister> dealRegisters = userData.getDealRegisters();
        List<FreeBoardComment> freeBoardComments = userData.getFreeBoardComments();
        List<DealBoardComment> dealBoardComments = userData.getDealBoardComments();

        /* 유저 프로필 테이블에서 데이터 가져오기 */
        String imageUrl = userProfileData.getImageUrl();
        String introduction = userProfileData.getIntroduction();
        int timePay = userData.getUserProfile().getTimepay();

        /* 생성자를 사용하여 객체 생성 */
        // GetResponseDTO getResponseDTO = new GetResponseDTO(id, imageUrl, nickName, location, introduction, timePay, freeRegisters, dealRegisters, freeBoardComments, dealBoardComments);
        GetResponseDTO getResponseDTO = new GetResponseDTO(id, imageUrl, nickName, location, introduction, timePay, boards, comments);
        return getResponseDTO;
    }

    @Transactional(readOnly = true)
    public GetResponseDTO getMyInfo(Authentication auth){
        String userEmail = auth.getName();
        User userData = userRepository.findByEmail(userEmail).orElseThrow(IllegalArgumentException::new);

        /* 유저 테이블에서 데이터 가져오기 */
        String nickName = userData.getNickname();
        String location = userData.getLocation();
        List<FreeRegister> freeRegisters = userData.getFreeRegisters();
        List<DealRegister> dealRegisters = userData.getDealRegisters();
        List<FreeBoardComment> freeBoardComments = userData.getFreeBoardComments();
        List<DealBoardComment> dealBoardComments = userData.getDealBoardComments();

        /* 유저 프로필 테이블에서 데이터 가져오기 */
        String imageUrl = userData.getUserProfile().getImageUrl();
        String introcudtion = userData.getUserProfile().getIntroduction();
        int timePay = userData.getUserProfile().getTimepay();

        /* 생성자를 사용하여 객체 생성 */
        GetResponseDTO getResponseDTO = new GetResponseDTO(userData.getUserId(), imageUrl, nickName, location, introcudtion, timePay, freeRegisters, dealRegisters, freeBoardComments, dealBoardComments);
        return getResponseDTO;
    }

    @Transactional
    public void deleteUserInfo(Authentication auth){
        String userEmail = auth.getName();
        User userData = userRepository.findByEmail(userEmail).orElseThrow(IllegalArgumentException::new);

        /* deleteById를 사용하지 않고 에러 메세지를 직접 커스텀 */
        if(userData != null) {
            UserProfile userProfileData = userData.getUserProfile();
            userRepository.delete(userData);
            userProfileRepository.delete(userProfileData);

        } else{
            System.out.println("존재하지 않는 회원이랍니다~");
        }

    }

    public String imageUpload(MultipartFile image) throws IOException, FirebaseAuthException {
        if (image != null) {
            if (!image.isEmpty()) {
                // 이미지를 업로드하고 해당 url 저장
                String imageUrl = firebaseService.uploadFiles(image);
                System.out.println(imageUrl);

                return imageUrl;
            } else {

                return null;
            }
        } else{
            return null;
        }
    }

    @Transactional
    public User signUpUser(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 유저입니다."));
        user.setSignUp(true);
        userRepository.save(user);
        return user;
    }

}