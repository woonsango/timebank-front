package com.capstone.timepay.controller.admin.response;

import com.capstone.timepay.domain.notification.Notification;
import com.capstone.timepay.service.admin.dto.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController("response.NotificationController")
@RequestMapping("api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    // 공지사항 리스트 반환
    @GetMapping("")
    public ResponseEntity<List<Notification>> getNotifications() {
        List<Notification> notificationList = this.notificationService.getList();
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }
}
