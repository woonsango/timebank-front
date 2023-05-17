package com.capstone.timepay.service.user.service;

import com.capstone.timepay.controller.admin.response.comment.CommentResponse;
import com.capstone.timepay.controller.user.request.BookmarkDTO;
import com.capstone.timepay.controller.user.request.RequestDTO;
import com.capstone.timepay.controller.user.request.UpdateRequestDTO;
import com.capstone.timepay.controller.user.response.GetResponseDTO;
import com.capstone.timepay.controller.user.response.UpdateResponseDTO;
import com.capstone.timepay.domain.board.BoardStatus;
import com.capstone.timepay.domain.comment.CommentRepository;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardRepository;
import com.capstone.timepay.domain.dealBoard.DealBoardSearch;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardCommentRepository;
import com.capstone.timepay.domain.dealBoardComment.DealBoardCommentSearch;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.domain.userProfile.UserProfile;
import com.capstone.timepay.domain.userProfile.UserProfileRepository;
import com.capstone.timepay.domain.userToken.UserToken;
import com.capstone.timepay.domain.userToken.UserTokenRepository;
import com.capstone.timepay.firebase.FirebaseService;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


/* 받은 데이터를 데이터베이스에 저장 */
@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final FirebaseService firebaseService;
    private final DealBoardRepository dealBoardRepository;
    private final DealBoardCommentRepository dealBoardCommentRepository;
    private final CommentRepository commentRepository;
    private final UserTokenRepository userTokenRepository;
    @Transactional
    public void createUserInfo(RequestDTO userData){
        /* 유저 프로필 데이터 저장 */
        Long uid = Long.parseLong(userData.getId());
        User user = userRepository.findById(uid).orElseThrow(()->new IllegalArgumentException("존재하지 않는 유저입니다."));


        /* uid값 비교하여 중복된 데이터는 데이터베이스에 저장X */
        UserProfile userProfile = user.getUserProfile();

        if(userData.getImageUrl() == null){
            userData.setImageUrl("");
        }
        userProfile.setImageUrl(userData.getImageUrl());
        userProfile.setIntroduction(userData.getIntroduction());
        userProfile.setCreatedAt(LocalDateTime.now());
        userProfile.setUpdatedAt(LocalDateTime.now());
        userProfile.setTimepay(300);
        user.setUserProfile(userProfileRepository.save(userProfile));



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
        user.setSignUp(true); // 회원가입 자동 승인 추후 뺄 예정

        
        /* 유저 토큰을 우선 빈문자열로 채움 */
        /* 추후 유저 토큰 테이블을 지우거나, 리프레쉬 토큰 사용 */
        UserToken userToken = new UserToken().builder()
                .accessToken("")
                .refrechToken("")
                .build();
        userTokenRepository.save(userToken);
        user.setUserToken(userToken);

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

        Page<DealBoard> dealBoards = new PageImpl<>(dealBoardRepository.findByDealRegistersIn(userData.getDealRegisters()),
                pageable, userData.getDealRegisters().size());

        Page<CommentResponse> dealBoardComments = new PageImpl<>(
                convertDCommentsToResponse(dealBoardCommentRepository.findAllByUser(userData)), pageable, pageSize);

        /* 유저 프로필 테이블에서 데이터 가져오기 */
        String imageUrl = userProfileData.getImageUrl();
        String introduction = userProfileData.getIntroduction();
        int timePay = userData.getUserProfile().getTimepay();


        /* 생성자를 사용하여 객체 생성 */
        GetResponseDTO getResponseDTO = new GetResponseDTO(id, imageUrl, nickName, location, introduction, timePay, dealBoards, dealBoardComments);
        return getResponseDTO;
    }

    @Transactional(readOnly = true)
    public GetResponseDTO getUserInfoBoard(Long id, int pageIndex, int pageSize){
        User userData = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 유저입니다."));
        UserProfile userProfileData = userData.getUserProfile();
        Pageable pageable = PageRequest.of(pageIndex, pageSize);


        /* 유저 테이블에서 데이터 가져오기 */
        String nickName = userData.getNickname();
        String location = userData.getLocation();

        Page<DealBoard> dealBoards = new PageImpl<>(dealBoardRepository.findByDealRegistersIn(userData.getDealRegisters()),
                pageable, userData.getDealRegisters().size());

        /* 유저 프로필 테이블에서 데이터 가져오기 */
        String imageUrl = userProfileData.getImageUrl();
        String introduction = userProfileData.getIntroduction();
        int timePay = userData.getUserProfile().getTimepay();


        /* 생성자를 사용하여 객체 생성 */
        GetResponseDTO getResponseDTO = new GetResponseDTO(id, imageUrl, nickName, location, introduction, timePay, dealBoards);
        return getResponseDTO;
    }

    @Transactional(readOnly = true)
    public Page<CommentResponse> getUserInfoComment(Long id, int pageIndex, int pageSize){
        User userData = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("존재하지 않는 유저입니다."));
        Pageable pageable = PageRequest.of(pageIndex, pageSize);


        Page<CommentResponse> dealBoardComments = new CustomPageImpl<>(
                convertDCommentsToResponse(dealBoardCommentRepository.findAllByUser(userData)), pageable);


        return (dealBoardComments);
    }

    @Transactional(readOnly = true)
    public GetResponseDTO getMyInfo(Authentication auth, int pageIndex, int pageSize){
        String userEmail = auth.getName();
        User userData = userRepository.findByEmail(userEmail).orElseThrow(IllegalArgumentException::new);
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        /* 유저 테이블에서 데이터 가져오기 */
        String nickName = userData.getNickname();
        String location = userData.getLocation();

        List<DealRegister> dealRegisters = userData.getDealRegisters();
        Page<DealBoard> dealBoards = new PageImpl<>(dealBoardRepository.findByDealRegistersIn(userData.getDealRegisters()),
                pageable, userData.getDealRegisters().size());

        Page<CommentResponse> dealBoardComments = new PageImpl<>(
                convertDCommentsToResponse(dealBoardCommentRepository.findAllByUser(userData)), pageable, pageSize);


        /* 유저 프로필 테이블에서 데이터 가져오기 */
        String imageUrl = userData.getUserProfile().getImageUrl();
        String introduction = userData.getUserProfile().getIntroduction();
        int timePay = userData.getUserProfile().getTimepay();

        /* 생성자를 사용하여 객체 생성 */
        GetResponseDTO getResponseDTO = new GetResponseDTO(userData.getUserId(), imageUrl, nickName, location, introduction, timePay, dealBoards, dealBoardComments);
        return getResponseDTO;
    }

    @Transactional(readOnly = true)
    public GetResponseDTO getMyInfoBoard(Authentication auth, int pageIndex, int pageSize, Specification<DealBoard> spec){
        String userEmail = auth.getName();
        User userData = userRepository.findByEmail(userEmail).orElseThrow(IllegalArgumentException::new);
        Pageable pageable = PageRequest.of(pageIndex, pageSize);


        /* 유저 테이블에서 데이터 가져오기 */
        String nickName = userData.getNickname();
        String location = userData.getLocation();

        Page<DealBoard> dealBoards = dealBoardRepository.findAll(Specification.where(spec)
                .and(DealBoardSearch.withDealRegisters(userData.getDealRegisters())), pageable);

        /* 유저 프로필 테이블에서 데이터 가져오기 */
        String imageUrl = userData.getUserProfile().getImageUrl();
        String introduction = userData.getUserProfile().getIntroduction();
        int timePay = userData.getUserProfile().getTimepay();

        /* 생성자를 사용하여 객체 생성 */
        GetResponseDTO getResponseDTO = new GetResponseDTO(userData.getUserId(), imageUrl, nickName, location, introduction, timePay, dealBoards);
        return getResponseDTO;
    }

    @Transactional(readOnly = true)
    public Page<CommentResponse> getMyInfoComment(Authentication auth, int pageIndex, int pageSize, Specification<DealBoardComment> spec){
        String userEmail = auth.getName();
        User userData = userRepository.findByEmail(userEmail).orElseThrow(IllegalArgumentException::new);
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

//        Page<CommentResponse> dealBoardComments = new PageImpl<>(
//                convertDCommentsToResponse(dealBoardCommentRepository.findAllByUser(userData)), pageable, pageSize);

//        Page<CommentResponse> dealBoardComments = new PageImpl<>(
//                convertDCommentsToResponse(
//                dealBoardCommentRepository.findAll(Specification
//                .where(DealBoardCommentSearch.withUser(userData))
//                .and(spec))), pageable, pageSize);

        Page<CommentResponse> dealBoardComments = new CustomPageImpl<>(
                convertDCommentsToResponse(dealBoardCommentRepository.findAll(Specification.where(DealBoardCommentSearch.withUser(userData)).and(spec))), pageable);

        return (dealBoardComments);
    }

    @Transactional
    public void deleteUserInfo(Authentication auth){
        User userData = userRepository.findByEmail(auth.getName()).orElseThrow(IllegalArgumentException::new);

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

    public List<CommentResponse> convertDCommentsToResponse(List<DealBoardComment> dComments){
        return dComments
                .stream()
                .map(dealBoardComment ->
                        CommentResponse.builder()
                                .commentId(commentRepository.findByDealBoardComment(dealBoardComment).getCommentId())
                                .originBoardId(dealBoardComment.getDealBoard().getD_boardId())
                                .originCommentId(dealBoardComment.getD_commentId())
                                .applyYN(dealBoardComment.isApplied())
                                .selectYN(dealBoardComment.isAdopted())
                                .boardTitle(dealBoardComment.getDealBoard().getTitle())
                                .content(dealBoardComment.getContent())
                                .writerId(dealBoardComment.getUser().getUserId())// dealBoardComment.getUser().getUserId()
                                .writerName(dealBoardComment.getUser().getName()) //dealBoardComment.getUser().getName()
                                .writtenTime(dealBoardComment.getCreatedAt())
                                .originWriterYN(true)
                                .writerNickname(dealBoardComment.getUser().getNickname()) //dealBoardComment.getUser().getNickname()
                                .isHidden(dealBoardComment.isHidden())
                                .updatedTime(dealBoardComment.getUpdatedAt())
                                .build())
                .collect(Collectors.toList());
    }

    public void saveBookmark(BookmarkDTO bookmarkDTO){
        User user = userRepository.findById(bookmarkDTO.getId()).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 유저입니다."));
        user.setBookmark(bookmarkDTO.getBookmark());
        userRepository.save(user);
    }

    public void updateBookmark(BookmarkDTO bookmarkDTO, Principal principal){
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 유저입니다."));
        user.setBookmark(bookmarkDTO.getBookmark());
        userRepository.save(user);
    }
}