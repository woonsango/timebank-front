package com.capstone.timepay.controller.admin;

import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.domain.notification.Notification;
import com.capstone.timepay.service.admin.AdminService;
import com.capstone.timepay.service.admin.dto.DeleteNotificationDTO;
import com.capstone.timepay.service.admin.NotificationService;
import com.capstone.timepay.service.admin.dto.PostNotificationDTO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final AdminService adminService;

    @PostMapping("")
    @ApiOperation(value = "공지사항 생성")
    public ResponseEntity<Boolean> createNotification(@RequestBody PostNotificationDTO dto,
                                                      Principal principal) {
        Admin admin = this.adminService.getOne(principal.getName());
        boolean success = this.notificationService.create(dto, admin);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @DeleteMapping("/{notificationId}")
    @ApiOperation(value = "공지사항 삭제")
    public ResponseEntity<Map<String, Object>> deleteNotification(@PathVariable Long notificationId) {
        Map<String, Object> result = notificationService.delete(notificationId);

        if ((Boolean) result.get("success")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    @ApiOperation(value = "공지사항 여러개 삭제")
    public ResponseEntity<Map<String, Object>> deleteNotifications(@RequestBody DeleteNotificationDTO dto) {
        Map<String, Object> result = notificationService.delete(dto.getIds());

        if ((Boolean) result.get("success")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    @ApiOperation("전체 공지사항 리스트 조회")
    public ResponseEntity<Page<Notification>> getNotifications(
            @RequestParam(value = "pagingIndex", defaultValue = "0") int pagingIndex,
            @RequestParam(value = "pagingSize", defaultValue = "50") int pagingSize) {

        Page<Notification> paging = this.notificationService.getList(pagingIndex, pagingSize);
        if (paging.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(paging, HttpStatus.OK);
    }

    @GetMapping("/search")
    @ApiOperation("특정 공지사항 검색")
    public ResponseEntity<Page<Notification>> searchNotifications(
            @RequestParam("title") String title,
            @RequestParam(value = "pagingIndex", defaultValue = "0") int pagingIndex,
            @RequestParam(value = "pagingSize", defaultValue = "50") int pagingSize) {

        System.out.println(title);
        Page<Notification> paging = this.notificationService.search(pagingIndex, pagingSize, title);
        if (paging.isEmpty()) {
            System.out.println("no content");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(paging, HttpStatus.OK);
    }

    @GetMapping("/{notificationId}")
    @ApiOperation("특정 id값 공지사항 조회")
    public ResponseEntity<Notification> getNotification(@PathVariable Long notificationId) {
        return new ResponseEntity<>(this.notificationService.getOne(notificationId).get(), HttpStatus.OK);
    }
}
