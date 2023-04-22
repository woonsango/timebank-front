package com.capstone.timepay.service.admin;

import com.capstone.timepay.controller.admin.response.auth.AdminInfoResponse;
import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.domain.admin.AdminRepository;
import com.capstone.timepay.service.admin.dto.AdminRegisterDTO;
import com.capstone.timepay.service.admin.dto.PasswordChangeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<AdminInfoResponse> getList(int pagingIndex, int pagingSize) {
        Pageable pageable = PageRequest.of(pagingIndex, pagingSize);
        Page<Admin> admins = adminRepository.findAll(pageable);
        return admins.map(AdminInfoResponse::new);
    }

    public Optional<AdminInfoResponse> getOne(Long adminId) {
        return this.adminRepository.findById(adminId)
                .map(AdminInfoResponse::new);
    }

    public Optional<AdminInfoResponse> getOne(String adminName) {
        return this.adminRepository.findByAdminName(adminName)
                .map(AdminInfoResponse::new);
    }

    public Admin getAdmin(String adminName) {
        return this.adminRepository.findByAdminName(adminName).get();
    }

    public Map<String, Object> create(AdminRegisterDTO dto) {
        Map<String, Object> resultMap = new HashMap<>();
        
        // 아이디가 중복되었을 때
        if (this.adminRepository.findByAdminName(dto.getAdminName()).isPresent()) {
            resultMap.put("success", false);
            resultMap.put("message", "중복된 아이디입니다.");

            return resultMap;
        }

        System.out.println(dto);
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        Admin newAdmin = Admin.builder()
                .adminName(dto.getAdminName())
                .password(encodedPassword)
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .isFirst(true)
                .roles(Collections.singletonList("ROLE_ADMIN"))
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

    public Map<String, Object> changePassword(PasswordChangeDTO dto, String adminName) {
        Map<String, Object> result = new HashMap<>();
        if (passwordEncoder.matches(dto.getCurrentPassword(), this.getOne(adminName).get().getPassword())) {
            if (!dto.getNewPassword().equals(dto.getNewPassword2())) {
                result.put("success", false);
                result.put("message", "비밀번호1과 비밀번호2가 일치하지 않습니다.");
            } else {
                Admin admin = this.getAdmin(adminName).updatePassword(passwordEncoder.encode(dto.getNewPassword()))
                        .updateFirst(false);
                adminRepository.save(admin);
                result.put("success", true);
                result.put("message", "비밀번호가 성공적으로 변경되었습니다.");
            }
        } else {
            result.put("success", false);
            result.put("message", "현재 비밀번호와 일치하지 않습니다.");
        }

        return result;
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

}
