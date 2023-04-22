package com.capstone.timepay.controller.admin.response.notification;

import com.capstone.timepay.domain.notification.Notification;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationInfoResponse {

    private Long notificationId;

    private String title;
    private String imageUrl;
    private boolean isNotice;
    private String content;
    private boolean isViewed;

    private Long adminId;
    private String adminName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public NotificationInfoResponse(Notification notification) {
        this.notificationId = notification.getNotificationId();
        this.title = notification.getTitle();
        this.imageUrl = notification.getImageUrl();
        this.isNotice = notification.isNotice();
        this.content = notification.getContent();
        this.adminId = notification.getAdmin().getAdminId();
        this.adminName = notification.getAdmin().getAdminName();
        this.createdAt = notification.getCreatedAt();
        this.updatedAt = notification.getUpdatedAt();
    }
}
