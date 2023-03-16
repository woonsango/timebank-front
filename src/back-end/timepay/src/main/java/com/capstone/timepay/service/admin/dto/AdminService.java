package com.capstone.timepay.service.admin.dto;

import com.capstone.timepay.DataNotFoundException;
import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.domain.admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public List<Admin> getList() {
        return this.adminRepository.findAll();
    }

    public Admin getAdmin(Long id) {
        Optional<Admin> admin = this.adminRepository.findById(id);
        if (admin.isPresent()) {
            return admin.get();
        } else {
            throw new DataNotFoundException("Admin is not found");
        }
    }

    public boolean create(Admin admin) {
        try {
            this.adminRepository.save(admin);
            return true;
        } catch (Exception e) {
            return false;
        }
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
