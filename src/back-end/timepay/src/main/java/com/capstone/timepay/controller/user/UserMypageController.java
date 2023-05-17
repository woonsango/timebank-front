package com.capstone.timepay.controller.user;

import com.capstone.timepay.controller.admin.response.comment.CommentResponse;
import com.capstone.timepay.controller.user.request.BookmarkDTO;
import com.capstone.timepay.controller.user.request.UpdateRequestDTO;
import com.capstone.timepay.controller.user.response.CertificationListResponse;
import com.capstone.timepay.controller.user.response.GetResponseDTO;
import com.capstone.timepay.domain.board.BoardStatus;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardSearch;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardComment.DealBoardCommentSearch;
import com.capstone.timepay.service.organization.OrganizationManageService;
import com.capstone.timepay.service.user.service.UserInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/mypage")
public class UserMypageController {

    private final OrganizationManageService organizationManageService;
    private final UserInfoService userInfoService;

    @Transactional(readOnly = true)
    @GetMapping("")
    @ApiOperation(value="마이페이지 조회",notes = "JWT 토큰에 해당하는 유저의 프로필 정보를 조회합니다.(마이페이지)")
    public ResponseEntity<?> getMyInfo(@RequestParam(defaultValue = "0") int pageIndex,
                                       @RequestParam(defaultValue = "10") int pageSize){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        GetResponseDTO responseData = userInfoService.getMyInfo(auth, pageIndex, pageSize);
        return ResponseEntity.ok(responseData);
    }


    //    현재 사용자 활동 관련 게시글 조회 api - pageIndex, pageSize, boardType, boardStatus
    @Transactional(readOnly = true)
    @GetMapping("/board")
    @ApiOperation(value="마이페이지 게시글 조회",notes = "JWT 토큰에 해당하는 유저의 프로필 정보 및 게시글 조회")
    public ResponseEntity<?> getMyInfoBoard(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                            @RequestParam(value = "boardStatus", required = false) BoardStatus boardStatus,
                                            @RequestParam(value = "boardType", required = false) String boardType
                                            ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Specification<DealBoard> spec = Specification.where(null);

        if (boardStatus != null) {
            spec = spec.and(DealBoardSearch.withBoardStatus(boardStatus));
        }

        if (boardType != null) {
            spec = spec.and(DealBoardSearch.withType(boardType));
        }

        GetResponseDTO responseData = userInfoService.getMyInfoBoard(auth, pageIndex, pageSize, spec);
        return ResponseEntity.ok(responseData);
    }


    //    현재 사용자 활동 관련 댓글 조회 api - pageIndex, pageSize, commentType
    @Transactional(readOnly = true)
    @GetMapping("/comment")
    @ApiOperation(value="마이페이지 댓글 조회",notes = "JWT 토큰에 해당하는 유저의 댓글 조회(마이페이지)")
    public ResponseEntity<?> getMyInfoComment(@RequestParam(defaultValue = "0") int pageIndex,
                                              @RequestParam(defaultValue = "10") int pageSize,
                                              @RequestParam(required = false) Boolean isApplied,
                                              @RequestParam(required = false) Boolean isAdopted){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Specification<DealBoardComment> spec = Specification.where(null);

        if (isApplied != null) {
            spec = spec.and(DealBoardCommentSearch.withApplied(isApplied));
        }

        if (isAdopted != null) {
            spec = spec.and(DealBoardCommentSearch.withAdopted(isAdopted));
        }

        return ResponseEntity.ok(userInfoService.getMyInfoComment(auth, pageIndex, pageSize, spec));
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    @ApiOperation(value="유저 데이터 조회",notes = "주소로 id를 받아 해당하는 유저 정보를 조회합니다.")
    public ResponseEntity<?> getUserInfo(@PathVariable Long id,
                                         @RequestParam(defaultValue = "0") int pageIndex,
                                         @RequestParam(defaultValue = "10") int pageSize){
        GetResponseDTO responseData = userInfoService.getUserInfo(id, pageIndex, pageSize);
        return ResponseEntity.ok(responseData);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}/board")
    @ApiOperation(value="유저 게시글 조회",notes = "주소로 id를 받아 해당하는 유저 정보 및 게시글 조회")
    public ResponseEntity<?> getUserInfoBoard(@PathVariable Long id,
                                         @RequestParam(defaultValue = "0") int pageIndex,
                                         @RequestParam(defaultValue = "10") int pageSize){
        GetResponseDTO responseData = userInfoService.getUserInfoBoard(id, pageIndex, pageSize);
        return ResponseEntity.ok(responseData);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}/comment")
    @ApiOperation(value="유저 댓글 조회",notes = "주소로 id를 받아 해당하는 유저의 댓글 조회")
    public ResponseEntity<?> getUserInfoComment(@PathVariable Long id,
                                         @RequestParam(defaultValue = "0") int pageIndex,
                                         @RequestParam(defaultValue = "10") int pageSize){

        return ResponseEntity.ok(userInfoService.getUserInfoComment(id, pageIndex, pageSize));
    }

    @ApiOperation(value = "봉사활동 인증서 내역")
    @GetMapping("/certification")
    public ResponseEntity<?> getCertificationList(Principal principal,
                                                  @RequestParam(defaultValue = "0") int pageIndex,
                                                  @RequestParam(defaultValue = "10") int pageSize){

        CertificationListResponse response = organizationManageService.getCertificationList(principal.getName(),pageIndex,pageSize);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/bookmark")
    @ApiOperation(value="마이페이지에서 유저 북마크(관심 카테고리) 설정")
    public ResponseEntity<?> postBookmarkUpdate(@RequestBody BookmarkDTO bookmarkDTO, Principal principal) {

        userInfoService.updateBookmark(bookmarkDTO, principal);

        return ResponseEntity.ok(bookmarkDTO.getBookmark() + " 정상적으로 처리되었습니다.");
    }

    @PutMapping("/update")
    @ApiOperation(value="유저 데이터 수정",notes = "Email을 이용하여 유저를 매핑하고 데이터를 수정합니다.")
    public ResponseEntity<?> putUserInfo(@ModelAttribute UpdateRequestDTO updateRequestData, @RequestPart(required = false) MultipartFile image) throws Exception{

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(userInfoService.updateUserInfo(auth, updateRequestData, image));
    }
}
