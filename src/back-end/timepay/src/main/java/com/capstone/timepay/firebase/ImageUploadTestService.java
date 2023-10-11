package com.capstone.timepay.firebase;

import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageUploadTestService {

    private final FirebaseService firebaseService;

    // Image upload를 하려면 IOException과 FirebaseAuthException 예외 처리를 해줘야 합니다.
    public void imageUploadTest(MultipartFile image) throws IOException, FirebaseAuthException {

        // 이미지를 업로드하고 해당 url 저장
        String imageUrl = firebaseService.uploadFiles(image);
        System.out.println(imageUrl);

        // 위의 url 주소를 저장할 테이블의 객체를 생성할 때 사용
        // ex ) UserProfile profile = UserProfile.builder().imageUrl(imageUrl). ........  . build();

        // url을 포함한 객체를 생성하고 해당 객체를 repository를 통해 테이블에 저장
        // ex ) userProfileRepository.save(profile);
    }
}
