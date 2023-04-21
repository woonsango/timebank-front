package com.capstone.timepay.service.organization;

import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.domain.organization.Organization;
import com.capstone.timepay.domain.organization.OrganizationRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.service.admin.dto.AdminRegisterDTO;
import com.capstone.timepay.service.organization.dto.OrgaUserSignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrganizationUserService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public Map<String, Object> create(OrgaUserSignUpDto dto) {
        Map<String, Object> resultMap = new HashMap<>();

        // 아이디가 중복되었을 때
        if (organizationRepository.findByAccount(dto.getId()).isPresent()) {
            resultMap.put("success", false);
            resultMap.put("message", "중복된 아이디입니다.");

            return resultMap;
        }

        String encodedPassword = passwordEncoder.encode(dto.getPw());
        Organization newOrganization = Organization.builder()
                .organizationName(dto.getOrganizationName())
                .businessCode(dto.getBusinessCode())
                .account(dto.getId())
                .pw(encodedPassword)
                .employeeNum(dto.getEmployeeNum())
                .timepay(dto.getTimepay())
                .authority("normal")
                .build();
        User newUser = User.builder()
                .birthday(null)
                .email(null)    // 나중에 추가로 받을 수도 있을 듯
                .encodedPassword(null)
                .isBanned(false)
                .isSignUp(false)
                .location(null)
                .name(dto.getManagerName()) // 담당자 이름 저장
                .nickname(null)
                .phone(dto.getManagerPhone())   // 담당자 폰 번호 저장
                .sex(null)
                .organization(newOrganization)
                .build();
        try {
            organizationRepository.save(newOrganization);
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
}
