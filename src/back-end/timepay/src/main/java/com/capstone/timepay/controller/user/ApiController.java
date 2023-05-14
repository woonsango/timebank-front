package com.capstone.timepay.controller.user;

import com.capstone.timepay.controller.admin.response.category.CategoryResponse;
import com.capstone.timepay.controller.user.request.BookmarkDTO;
import com.capstone.timepay.controller.user.request.RequestDTO;
import com.capstone.timepay.controller.user.request.UpdateRequestDTO;
import com.capstone.timepay.controller.user.response.GetResponseDTO;
import com.capstone.timepay.controller.user.response.UpdateResponseDTO;
import com.capstone.timepay.service.admin.CategoryManageService;
import com.capstone.timepay.service.user.service.UserInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class ApiController {
    private final UserInfoService userInfoService;
    private final CategoryManageService categoryManageService;
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

    @PostMapping("/create/bookmark")
    @ApiOperation(value="회원가입할 때, 유저 북마크(관심 카테고리) 설정")
    public ResponseEntity<?> postBookmark(@RequestBody BookmarkDTO bookmarkDTO) {

        userInfoService.saveBookmark(bookmarkDTO);

        return ResponseEntity.ok(bookmarkDTO.getBookmark() + " 정상적으로 처리되었습니다." + HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value="유저 데이터 삭제(회원탈퇴)",notes = "JWT 토큰에 해당하는 유저 정보를 삭제합니다.")
    public ResponseEntity<?> deleteUserInfo() {
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

    // ------------------------- 유저 카테고리 목록 불러오기 ----------------------------------
    @ApiOperation(value = "가능한 카테고리 목록 조회")
    @GetMapping("/category")
    public ResponseEntity<?> loadCategory(@RequestParam String type, @RequestParam String useYn){

        List<CategoryResponse> responses = categoryManageService.showPossibleCategories(type, useYn);

        return ResponseEntity.ok(responses);
    }
    // -----------------------------------------------------------------------------------
}
