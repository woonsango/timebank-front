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
import org.springframework.transaction.annotation.Transactional;
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
    @ApiOperation(value="유저 데이터 생성",notes = "Email을 이용하여 유저 테이블과 유저 프로필 테이블을 매핑하고, DB에 데이터를 생성합니다.")
    public ResponseEntity<?> postKakaoData(@ModelAttribute RequestDTO requestData, @RequestPart(required = false) MultipartFile image) throws Exception {

        requestData.setImageUrl(userInfoService.imageUpload(image));
        userInfoService.createUserInfo(requestData);

        return ResponseEntity.ok(requestData);
    }

    @Transactional(readOnly = true)
    @GetMapping("/get/{id}")
    @ApiOperation(value="유저 데이터 조회",notes = "주소로 id를 받아 해당하는 유저 정보를 조회합니다.")
    public ResponseEntity<?> getUserInfo(@PathVariable Long id, @RequestParam(defaultValue = "0") int pageIndex,
                                         @RequestParam(defaultValue = "50") int pageSize){
        GetResponseDTO responseData = userInfoService.getUserInfo(id, pageIndex, pageSize);
        return ResponseEntity.ok(responseData);
    }

    @Transactional(readOnly = true)
    @GetMapping("/get")
    @ApiOperation(value="마이페이지 조회",notes = "JWT 토큰에 해당하는 유저의 프로필 정보를 조회합니다.(마이페이지)")
    public ResponseEntity<?> getMyInfo(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        GetResponseDTO responseData = userInfoService.getMyInfo(auth);
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/update")
    @ApiOperation(value="유저 데이터 수정",notes = "Email을 이용하여 유저를 매핑하고 데이터를 수정합니다.")
    public ResponseEntity<?> putUserInfo(@ModelAttribute UpdateRequestDTO updateRequestData, @RequestPart(required = false) MultipartFile image) throws Exception{

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(userInfoService.updateUserInfo(auth, updateRequestData, image));
    }

    @DeleteMapping("/delete")
    @ApiOperation(value="유저 데이터 삭제(회원탈퇴)",notes = "JWT 토큰에 해당하는 유저 정보를 삭제합니다.")
    public ResponseEntity<?> deleteUserInfo2() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        userInfoService.deleteUserInfo(auth);
        return ResponseEntity.ok(auth.getName() + " Delete Success");
    }

    @PostMapping("/test/{id}")
    @ApiOperation(value="유저 회원가입 승인",notes = "회원가입 대기 목록에서 id에 해당하는 유저를 회원가입 처리합니다.")
    public ResponseEntity<?> signUpUser(@PathVariable Long id) throws Exception{
        return ResponseEntity.ok(userInfoService.signUpUser(id));
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
}
