package com.capstone.timepay.service.organization;

import com.capstone.timepay.controller.organization.response.CertificatePublishResponse;
import com.capstone.timepay.controller.organization.response.ParticipateUsers;
import com.capstone.timepay.controller.organization.response.VolunteerInfo;
import com.capstone.timepay.domain.certification.Certification;
import com.capstone.timepay.domain.certification.CertificationRepository;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardRepository;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardCommentRepository;
import com.capstone.timepay.domain.organization.OrganizationRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.firebase.FirebaseService;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrganizationManageService {
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FirebaseService firebaseService;
    private final DealBoardRepository dealBoardRepository;
    private final DealBoardCommentRepository dealBoardCommentRepository;
    private final CertificationRepository certificationRepository;

    public CertificatePublishResponse publishCertificate(Long boardId) {

        DealBoard dealBoard = dealBoardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
        if(!dealBoard.isVolunteer()) throw new IllegalArgumentException("봉사활동 게시글이 아닙니다.");

        // 선정된 사람들 찾아서 카운트 할 것.
        List<DealBoardComment> comments = dealBoardCommentRepository.findAllByDealBoardAndIsAdopted(dealBoard, true);

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
                        .userNickName(certification.getUser().getNickname())
                        .isPublished(certification.isPublished())
                        .imageUrl(certification.getUser().getUserProfile().getImageUrl())
                        .build())
                .collect(Collectors.toList());

    }

    public void activityComplete(Long boardId){
        DealBoard dealBoard = dealBoardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
        if(!dealBoard.isVolunteer()) throw new IllegalArgumentException("봉사활동 게시글이 아닙니다.");

        List<DealBoardComment> comments = dealBoardCommentRepository.findAllByDealBoardAndIsAdopted(dealBoard, true);

        for(DealBoardComment element : comments){
            Certification certification = Certification.builder()
                    .certificationUrl("none")
                    .dealBoardId(boardId)
                    .downloadCount(0)
                    .user(element.getUser())
                    .isPublished(false)
                    .build();
            certificationRepository.save(certification);
        }
    }

    public void publishUserCertificate(Long boardId, String nickname, MultipartFile certificationFile) throws IOException, FirebaseAuthException {

        User user = userRepository.findByNickname(nickname).orElseThrow(()->new IllegalArgumentException("존재하지 않는 유저입니다."));
        Certification certification = certificationRepository.findByUserAndDealBoardId(user,boardId);

        certification.updateCertificationUrl(firebaseService.uploadFiles(certificationFile));
        certification.updateIsPublished(true);

        certificationRepository.save(certification);

    }
}
