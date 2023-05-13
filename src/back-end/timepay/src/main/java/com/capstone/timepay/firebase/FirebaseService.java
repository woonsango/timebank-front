package com.capstone.timepay.firebase;

import com.capstone.timepay.firebase.dto.FCMDto;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class FirebaseService {

    @Value("${app.firebase-bucket}")
    private String firebaseBucket;
    Logger logger = org.slf4j.LoggerFactory.getLogger(FirebaseService.class);

    public String uploadFiles(MultipartFile file) throws IOException, FirebaseAuthException {
        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);
        InputStream content = new ByteArrayInputStream(file.getBytes());

        // 업로드 할 파일 이름 생성
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());

        // Firebase Storage에 업로드 된 이미지 URL return
        return "https://storage.googleapis.com/" + bucket.getName() + "/" + blob.getName();
    }

    public boolean sendMessage(FCMDto dto) throws ExecutionException, InterruptedException {
        Message message = Message.builder()
                .setNotification(com.google.firebase.messaging.Notification.builder()
                .setTitle(dto.getTitle())
                .setBody(dto.getBody())
                .setImage(dto.getImageUrl())
                .build())
                .setToken(dto.getToken())
                .build();
        try {
            String response = FirebaseMessaging.getInstance().sendAsync(message).get();
            logger.debug("Successfully sent message: " + response);
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

}
