package com.capstone.timepay.firebase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/firebase")
@RequiredArgsConstructor
public class ImageUploadTestController {

    private final ImageUploadTestService imageUploadTestService;

    // Multipart form data를 RestApi의 요청으로 받으려면 MediaType을 명시해야 합니다. Multipart form data 외에 json data를 함께 받고 싶으면 아래와 같이 입력하세요.
    // @PostMapping(value ="test", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @PostMapping(value ="/test", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> imageUploadTest( @RequestPart MultipartFile image) throws Exception{   // throws Exception 해주세요

        imageUploadTestService.imageUploadTest(image);

        return ResponseEntity.ok("Image Upload Complete");
    }

}
