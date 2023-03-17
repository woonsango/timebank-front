package com.capstone.timepay.service.admin.dto;

import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.domain.admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<Admin> getList() {
        return this.adminRepository.findAll();
    }

    public Map<String, Object> create(Admin admin) {
        Map<String, Object> resultMap = new HashMap<>();
        
        // 아이디가 중복되었을 때
        if (this.adminRepository.findByAdminName(admin.getAdminName()).isPresent()) {
            resultMap.put("success", false);
            resultMap.put("message", "중복된 아이디입니다.");

            return resultMap;
        }

        String encodedPassword = passwordEncoder.encode(admin.getPassword());
        Admin newAdmin = Admin.builder()
                .adminName(admin.getAdminName())
                .password(encodedPassword)
                .authority(admin.getAuthority())
                .name(admin.getName())
                .email(admin.getEmail())
                .phone(admin.getPhone())
                .build();

        try {
            this.adminRepository.save(newAdmin);
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }

    public Map<String, Object> delete(Long adminId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            adminRepository.deleteById(adminId);
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }
    
    //TODO: 로그인 기능 추가
}
