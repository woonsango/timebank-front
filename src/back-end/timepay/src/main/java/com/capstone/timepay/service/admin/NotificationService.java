package com.capstone.timepay.service.admin;

import com.capstone.timepay.controller.admin.response.notification.NotificationInfoResponse;
import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.domain.notification.Notification;
import com.capstone.timepay.domain.notification.NotificationRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.firebase.FirebaseService;
import com.capstone.timepay.service.admin.dto.NotificationPostDTO;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final FirebaseService firebaseService;

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
        List<User> userList = userRepository.findAll();
        Notification notification = Notification.builder()
                .title(dto.getTitle())
                .imageUrl(dto.getImageUrl())
                .isNotice(dto.isNotice())
                .content(dto.getContent())
                .admin(admin).build();

        this.notificationRepository.save(notification);

        for (User user: userList) {
            String token = user.getDeviceToken();
            if (token != null) {
                // 푸쉬 메세지
                Message message = Message.builder()
                        .setNotification(com.google.firebase.messaging.Notification.builder()
                                .setTitle(notification.getTitle())
                                .setBody(notification.getContent())
                                .setImage(notification.getImageUrl())
                                .build())
                        .setToken(token)
                        .build();
                try {
                    String response = FirebaseMessaging.getInstance().sendAsync(message).get();
                    System.out.println("Successfully sent message: " + response);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        return true;
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

    public String imageUpload(MultipartFile image) throws IOException, FirebaseAuthException {
        if(image.getSize() != 0) {
            // 이미지를 업로드하고 해당 url 저장
            String imageUrl = firebaseService.uploadFiles(image);
            System.out.println(imageUrl);

            return imageUrl;
        } else {

            return null;
        }
    }
}
