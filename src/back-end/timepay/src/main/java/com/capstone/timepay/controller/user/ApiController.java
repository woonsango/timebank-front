package com.capstone.timepay.controller.user;

import com.capstone.timepay.controller.user.request.RequestDTO;
import com.capstone.timepay.controller.user.request.UpdateRequestDTO;
import com.capstone.timepay.controller.user.response.GetResponseDTO;
import com.capstone.timepay.controller.user.response.UpdateResponseDTO;
import com.capstone.timepay.service.user.service.UserInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class ApiController {
    private final UserInfoService userInfoService;
    /* 회원가입 버튼 클릭하면 데이터가 Post로 format형식으로 넘어옴 */
    /* json 형식의 데이터를 받아서 createUserService로 넘겨줌 */
    /* 카카오 데이터와 어떻게 매칭해줄지 생각 필요 */

    @PostMapping("/create")
    @ApiOperation(value="유저 데이터 생성",notes = "uid를 이용하여 유저 테이블과 유저 프로필 테이블을 매핑하고, DB에 데이터를 생성합니다.")
    public ResponseEntity<?> postKakaoData(@ModelAttribute RequestDTO requestData, @RequestPart MultipartFile image) throws Exception {

        requestData.setImageUrl(userInfoService.imageUpload(image));
        userInfoService.createUserInfo(requestData);

        return ResponseEntity.ok(requestData);
    }

    @GetMapping("/get/{uid}")
    @ApiOperation(value="유저 데이터 조회",notes = "주소로 uid를 받아 해당하는 유저 정보를 조회합니다.")
    public ResponseEntity<?> getUserInfo(@PathVariable Long uid){
        GetResponseDTO responseData = userInfoService.getUserInfo(uid);
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/get")
    @ApiOperation(value="유저 데이터 조회",notes = "JWT 토큰에 해당하는 유저의 프로필 정보를 조회합니다.(마이페이지)")
    public ResponseEntity<?> getMyInfo(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        GetResponseDTO responseData = userInfoService.getMyInfo(auth);
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/update")
    @ApiOperation(value="유저 데이터 수정",notes = "uid를 이용하여 유저를 매핑하고 데이터를 수정합니다.")
    public ResponseEntity<?> putUserInfo(@ModelAttribute UpdateRequestDTO updateRequestData) throws Exception{

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(userInfoService.updateUserInfo(auth, updateRequestData));
    }

    /* 회원탈퇴 기능은 path로 UID를 받는 것이 아니라 */
    /* JWT 토큰으로 유저를 판별하여 탈퇴로 변경 */
//    @DeleteMapping("/delete/{uid}")
//    @ApiOperation(value="유저 데이터 삭제(회원탈퇴)",notes = "주소로 uid를 받아 해당하는 유저 정보를 삭제합니다.")
//    public ResponseEntity<?> deleteUserInfo(@PathVariable Long uid) {
//        userInfoService.deleteUserInfo2(uid);
//        return ResponseEntity.ok(uid + " Delete Success");
//    }

    @DeleteMapping("/delete")
    @ApiOperation(value="유저 데이터 삭제(회원탈퇴)",notes = "JWT 토큰에 해당하는 유저 정보를 삭제합니다.")
    public ResponseEntity<?> deleteUserInfo2() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        userInfoService.deleteUserInfo(auth);
        return ResponseEntity.ok(auth.getName() + " Delete Success");
    }

    @PostMapping("/test/{uid}")
    @ApiOperation(value="유저 회원가입 승인",notes = "회원가입 대기 목록에서 uid에 해당하는 유저를 회원가입 처리합니다.")
    public ResponseEntity<?> signUpUser(@PathVariable Long uid) throws Exception{
        return ResponseEntity.ok(userInfoService.signUpUser(uid));
    }

    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃 API", notes = "JWT 토큰으로 유저를 구분하여 로그아웃합니다.")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // 현재 인증된 사용자의 인증 토큰을 가져온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 토큰이 존재하면 로그아웃 처리를 한다.
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return ResponseEntity.ok("로그아웃되었습니다.");
    }

    @PostMapping("/report")
    @ApiOperation(value = "신고 API", notes = "JWT 토큰으로 유저를 구분하여 신고 DB에 작성합니다.")
    public ResponseEntity<?> report(HttpServletRequest request, HttpServletResponse response) {
        /* 현재 인증된 사용자의 인증 토큰을 가져온다.*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        /* 어떤 정보를 바탕으로 어느 신고인지 구분? */

        return ResponseEntity.ok("로그아웃되었습니다.");
    }


}
