package com.capstone.timepay.service.organization;

import com.capstone.timepay.controller.admin.response.inquiry.InquiryResponse;
import com.capstone.timepay.controller.organization.response.CertificatePublishResponse;
import com.capstone.timepay.controller.organization.response.ParticipateUsers;
import com.capstone.timepay.controller.organization.response.VolunteerInfo;
import com.capstone.timepay.controller.user.response.CertificationList;
import com.capstone.timepay.controller.user.response.CertificationListResponse;
import com.capstone.timepay.domain.certification.Certification;
import com.capstone.timepay.domain.certification.CertificationRepository;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardRepository;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardCommentRepository;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.dealRegister.DealRegisterRepository;
import com.capstone.timepay.domain.inquiry.Inquiry;
import com.capstone.timepay.domain.organization.OrganizationRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.firebase.FirebaseService;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrganizationManageService {
    private final UserRepository userRepository;
    private final FirebaseService firebaseService;
    private final DealBoardRepository dealBoardRepository;
    private final DealBoardCommentRepository dealBoardCommentRepository;
    private final CertificationRepository certificationRepository;
    private final DealRegisterRepository dealRegisterRepository;

    public CertificatePublishResponse publishCertificate(Long boardId) {

        DealBoard dealBoard = dealBoardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
        if(!dealBoard.isVolunteer()) throw new IllegalArgumentException("봉사활동 게시글이 아닙니다.");

        // 선정된 사람들 찾아서 카운트 할 것.
        List<DealBoardComment> comments = dealBoardCommentRepository.findAllByDealBoardAndIsAdoptedTrue(dealBoard);
        VolunteerInfo info = VolunteerInfo.builder()
                .title(dealBoard.getTitle())
                .participateNum(comments.size())
                .location(dealBoard.getLocation())
                .startTime(dealBoard.getStartTime())
                .endTime(dealBoard.getEndTime())
                .build();

        List<Certification> certifications = certificationRepository.findAllByDealBoardId(boardId);

        Page<ParticipateUsers> participateUsers = new PageImpl<>(convertToResponse(certifications));

        return CertificatePublishResponse.builder()
                .participateUsers(participateUsers)
                .volunteerInfo(info)
                .build();
    }

    private List<ParticipateUsers> convertToResponse(List<Certification> certifications) {
        return certifications.stream().map(certification -> ParticipateUsers.builder()
                        .userId(certification.getUser().getUserId())
                        .userName(certification.getUser().getName())
                        .email(certification.getUser().getEmail())
                        .phone(certification.getUser().getPhone())
                        .certificationUrl(certification.getCertificationUrl())
                        .userNickname(certification.getUser().getNickname())
                        .isPublished(certification.isPublished())
                        .build())
                .collect(Collectors.toList());

    }

    public void activityComplete(Long boardId){
        DealBoard dealBoard = dealBoardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
        if(!dealBoard.isVolunteer()) throw new IllegalArgumentException("봉사활동 게시글이 아닙니다.");

        List<DealBoardComment> comments = dealBoardCommentRepository.findAllByDealBoardAndIsAdoptedTrue(dealBoard);

        for(DealBoardComment element : comments){
            Certification certification = Certification.builder()
                    .certificationUrl("none")
                    .dealBoardId(boardId)
                    .downloadCount(0)
                    .user(element.getUser())
                    .isPublished(false)
                    .time(element.getDealBoard().getVolunteerTime())
                    .build();
            certificationRepository.save(certification);
        }
    }

    public void publishUserCertificate(Long boardId, Long userId, MultipartFile certificationFile) throws IOException, FirebaseAuthException {

        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 유저입니다."));
        Certification certification = certificationRepository.findByUserAndDealBoardId(user,boardId);

        user.updateTotalVolunteerTime(certification.getTime());
        certification.updateCertificationUrl(firebaseService.uploadFiles(certificationFile));
        certification.updateIsPublished(true);

        userRepository.save(user);
        certificationRepository.save(certification);

    }

    public CertificationListResponse getCertificationList(String email, int pageIndex, int pageSize) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("존재하지 않는 유저입니다."));
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createdAt").descending());

        Page<Certification> pages = certificationRepository.findAllByUserAndIsPublishedTrue(user,pageable);

        return CertificationListResponse.builder()
                .totalTime(user.getTotalVolunteerTime())
                .certificationListPage(convertResponsePages(pages))
                .build();
    }
    public Page<CertificationList> convertResponsePages(Page<Certification> pages){
        Page<CertificationList> pageResponses = pages.map(new Function<Certification, CertificationList>() {
            @Override
            public CertificationList apply(Certification certification) {
                DealBoard dealboard = dealBoardRepository.findById(certification.getDealBoardId()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
                DealRegister dealRegister = dealRegisterRepository.findByDealBoard(dealboard).orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
                return CertificationList.builder()
                        .boardId(dealboard.getD_boardId())
                        .title(dealboard.getTitle())
                        .organizationName(dealRegister.getUser().getOrganization().getOrganizationName())
                        .managerName(dealRegister.getUser().getName())
                        .managerPhone(dealRegister.getUser().getPhone())
                        .volunteerTime(certification.getTime())
                        .certificationUrl(certification.getCertificationUrl())
                        .state(certification.isPublished())
                        .build();
            }
        });

        return pageResponses;
    }
}
