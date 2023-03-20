package com.capstone.timepay.controller.admin.response;

import com.capstone.timepay.domain.notification.Notification;
import com.capstone.timepay.service.admin.NotificationService;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController("response.NotificationController")
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

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
