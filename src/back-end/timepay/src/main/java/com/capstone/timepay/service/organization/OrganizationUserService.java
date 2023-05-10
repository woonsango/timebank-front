package com.capstone.timepay.service.organization;

import com.capstone.timepay.controller.organization.request.OrgaUpdateRequest;
import com.capstone.timepay.controller.organization.response.CertificatePublishResponse;
import com.capstone.timepay.controller.organization.response.ParticipateUsers;
import com.capstone.timepay.controller.organization.response.VolunteerInfo;
import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.domain.certification.Certification;
import com.capstone.timepay.domain.certification.CertificationRepository;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardRepository;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardCommentRepository;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReport;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReportRepository;
import com.capstone.timepay.domain.organization.Organization;
import com.capstone.timepay.domain.organization.OrganizationRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.firebase.FirebaseService;
import com.capstone.timepay.service.admin.dto.AdminRegisterDTO;
import com.capstone.timepay.service.organization.dto.OrgaUserSignUpDto;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrganizationUserService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FirebaseService firebaseService;
    private final DealBoardRepository dealBoardRepository;
    private final DealBoardCommentRepository dealBoardCommentRepository;
    private final CertificationRepository certificationRepository;


    public Map<String, Object> create(OrgaUserSignUpDto dto, MultipartFile image, MultipartFile certification) throws IOException, FirebaseAuthException {
        Map<String, Object> resultMap = new HashMap<>();

        // 아이디가 중복되었을 때
        if (organizationRepository.findByAccount(dto.getId()).isPresent()) {
            resultMap.put("success", false);
            resultMap.put("message", "중복된 아이디입니다.");

            return resultMap;
        }

        String imageUrl = "none";
        String certificationUrl = "none";
        if(!ObjectUtils.isEmpty(image)) imageUrl = firebaseService.uploadFiles(image);
        if(!ObjectUtils.isEmpty(certification)) certificationUrl = firebaseService.uploadFiles(certification);

        String encodedPassword = passwordEncoder.encode(dto.getPw());


        try {
            Organization newOrganization = Organization.builder()
                .organizationName(dto.getOrganizationName())
                .businessCode(dto.getBusinessCode())
                .account(dto.getId())
                .pw(encodedPassword)
                .employeeNum(dto.getEmployeeNum())
                .timepay(dto.getTimepay())
                .authority("normal")    // 일단 기본 권한 부여
                .imageUrl(imageUrl)
                .certificationUrl(certificationUrl)
                .roles(Collections.singletonList("ROLE_ORGANIZATION"))
                .build();
            organizationRepository.save(newOrganization);
            User newUser = User.builder()
                    .birthday(null)
                    .email(null)    // 나중에 추가로 받을 수도 있을 듯
                    .encodedPassword(null)
                    .isBanned(false)
                    .isSignUp(false)
                    .location(null)
                    .name(dto.getManagerName()) // 담당자 이름 저장
                    .nickname(dto.getManagerName())
                    .phone(dto.getManagerPhone())   // 담당자 폰 번호 저장
                    .sex(null)
                    .organization(newOrganization)
                    .userProfile(null)
                    .userToken(null)
                    .totalVolunteerTime(0)
                    .build();
            userRepository.save(newUser);
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }

    public Map<String, Object> delete(String account) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            organizationRepository.deleteByAccount(account);
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }

    public void updateInfo(OrgaUpdateRequest request, MultipartFile image, MultipartFile certification, String account) throws IOException, FirebaseAuthException {
        Organization organization = organizationRepository.findByAccount(account).orElseThrow(()->new IllegalArgumentException("존재하지 않는 계정입니다."));
        User user = userRepository.findByOrganization(organization).orElseThrow(()->new IllegalArgumentException("존재하지 않는 계정입니다."));
        if(!Objects.isNull(request.getManagerName())) user.updateName(request.getManagerName());
        if(!Objects.isNull(request.getManagerPhone())) user.updatePhone(request.getManagerPhone());
        if(!Objects.isNull(image)){
            String imageUrl = firebaseService.uploadFiles(image);
            organization.updateImageUrl(imageUrl);
        }
        if(!Objects.isNull(certification)){
            String certificationUrl = firebaseService.uploadFiles(certification);
            organization.updateCertificationUrl(certificationUrl);
        }
        organizationRepository.save(organization);
        userRepository.save(user);
    }


}
