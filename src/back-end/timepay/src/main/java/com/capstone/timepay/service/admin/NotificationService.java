package com.capstone.timepay.service.admin;

import com.capstone.timepay.controller.admin.response.notification.NotificationInfoResponse;
import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.domain.notification.Notification;
import com.capstone.timepay.domain.notification.NotificationRepository;
import com.capstone.timepay.service.admin.dto.NotificationPostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public Page<NotificationInfoResponse> getList(int pagingIndex, int pagingSize) {
        Pageable pageable = PageRequest.of(pagingIndex, pagingSize, Sort.by("createdAt").descending());
        Page<Notification> notifications = this.notificationRepository.findAll(pageable);
        return notifications.map(NotificationInfoResponse::new);
    }

    public Page<NotificationInfoResponse> search(int pagingIndex, int pagingSize, String title) {
        Pageable pageable = PageRequest.of(pagingIndex, pagingSize, Sort.by("createdAt").descending());
        Page<Notification> notifications = this.notificationRepository.findByTitleContaining(title, pageable);
        return notifications.map(NotificationInfoResponse::new);
    }

    public Optional<NotificationInfoResponse> getOne(Long id) {
        return this.notificationRepository.findById(id)
                .map(NotificationInfoResponse::new);
    }

    public boolean create(NotificationPostDTO dto, Admin admin) {
        Notification notification = Notification.builder()
                .title(dto.getTitle())
                .imageUrl(dto.getImageUrl())
                .isNotice(dto.isNotice())
                .content(dto.getContent())
                .admin(admin).build();
        try {
            this.notificationRepository.save(notification);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Map<String, Object> delete(Long notificationId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            notificationRepository.deleteById(notificationId);
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }

    public Map<String, Object> delete(List<String> notificationIds) {
        Map<String, Object> resultMap = new HashMap<>();
        for (String id : notificationIds) {
            try {
                this.notificationRepository.deleteById(Long.valueOf(id));
                resultMap.put("success", true);
            } catch (Exception e) {
                resultMap.put("success", false);
                resultMap.put("message", e.getMessage());
            }
        }

        return resultMap;
    }
}
