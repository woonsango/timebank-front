package com.capstone.timepay.service.admin.dto;

import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.service.admin.AdminService;
import com.capstone.timepay.service.admin.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    AdminService adminService;

    @Autowired
    NotificationService notificationService;

    @Test
    void testJpa() {
        for (int i = 1; i <= 300; i++) {
            Admin admin = adminService.getOne(6l).get();
            NotificationPostDTO notification = new NotificationPostDTO();
            notification.setTitle(String.format("테스트 데이터입니다:[%03d]", i));
            notification.setContent("내용무");

            this.notificationService.create(notification, admin);
        }
    }

    @Test
    void getAdmin() {
    }
}