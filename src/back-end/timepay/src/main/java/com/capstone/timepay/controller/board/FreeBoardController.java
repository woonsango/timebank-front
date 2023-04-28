package com.capstone.timepay.controller.board;

import com.capstone.timepay.controller.board.request.ReportRequestDTO;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.service.board.dto.FreeBoardDTO;
import com.capstone.timepay.service.board.service.FreeBoardService;
import com.capstone.timepay.service.board.service.FreeRegisterService;
import com.capstone.timepay.service.board.service.ReportService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/free-boards")
public class FreeBoardController {

    private final FreeBoardService freeBoardService;

    private final ReportService reportService;

    private final FreeRegisterService freeRegisterService;


    @GetMapping("")
    @ApiOperation(value = "전체 자유게시판 조회")
    public ResponseEntity<Page<FreeBoardDTO>> getBoards(
            @RequestParam(value = "pagingIndex", defaultValue = "0") int pagingIndex,
            @RequestParam(value = "pagingSize", defaultValue = "50") int pagingSize)
    {
        Page<FreeBoardDTO> paging = freeBoardService.getBoards(pagingIndex, pagingSize);
        if (paging.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(paging, HttpStatus.OK);
    }

    @ApiOperation(value = "개별 자유게시판 조회")
    @GetMapping("/{id}")
    public ResponseEntity<FreeBoardDTO> getBoard(@PathVariable("id") Long id)
    {
        return new ResponseEntity<>(freeBoardService.getBoard(id), HttpStatus.OK);
    }


    @ApiOperation(value = "자유게시글 작성")
    @PostMapping(value = "/write", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity write(@RequestPart FreeBoardDTO freeBoardDTO,
                                @RequestPart(required = false) List<MultipartFile> images,
                                Principal principal) throws Exception
    {
        return new ResponseEntity(freeBoardService.write(freeBoardDTO, principal.getName(), images), HttpStatus.CREATED);
    }

    @ApiOperation(value = "자유게시글 수정")
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@RequestBody FreeBoardDTO freeBoardDTO,
                                                      @PathVariable("id") Long id,
                                                      Principal principal)
    {
        try {
            Map<String, Object> updateMap = new HashMap<>();
            FreeBoard freeBoard = freeBoardService.getId(id);
            String boardEmail = freeRegisterService.getEmail(id);

            if (freeBoard == null) {
                updateMap.put("success", false);
                updateMap.put("message", "해당 게시글을 찾을 수 없습니다.");
                return new ResponseEntity<>(updateMap, HttpStatus.NOT_FOUND);
            }

            if (!principal.getName().equals(boardEmail)) {
                updateMap.put("success", false);
                updateMap.put("message", "수정 권한이 없습니다");
                return new ResponseEntity<>(updateMap, HttpStatus.UNAUTHORIZED);
            }

            freeBoardService.update(id, freeBoardDTO);
            updateMap.put("success", true);
            updateMap.put("freeBoard", freeBoard);
            return new ResponseEntity<>(updateMap, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("success", false);
            errorMap.put("message", "서버 에러: " + e.getMessage());
            return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "자유게시글 삭제")
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@RequestBody FreeBoardDTO freeBoardDTO,
                                                      @PathVariable("id") Long id,
                                                      Principal principal)
    {
        Map<String, Object> deleteMap = new HashMap<>();
        String boardEmail = freeRegisterService.getEmail(id);

        try {
            FreeBoard freeBoard = freeBoardService.getId(id);
            if (freeBoard == null) {
                deleteMap.put("success", false);
                deleteMap.put("message", "해당 게시글을 찾을 수 없습니다.");
                return new ResponseEntity<>(deleteMap, HttpStatus.NOT_FOUND);
            }
            if (!principal.getName().equals(boardEmail)) {
                deleteMap.put("success", false);
                deleteMap.put("message", "삭제 권한이 없습니다");
                return new ResponseEntity<>(deleteMap, HttpStatus.UNAUTHORIZED);
            }
            freeBoardService.delete(id);
            deleteMap.put("success", true);
            return ResponseEntity.ok(deleteMap);
        } catch (Exception e) {
            deleteMap.put("success", false);
            deleteMap.put("message", "게시글 삭제 중 오류가 발생했습니다.");
            return new ResponseEntity<>(deleteMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/{boardId}/report")
    @ApiOperation(value = "신고 API", notes = "JWT 토큰으로 유저를 구분하여 신고 DB에 작성합니다.")
    public ResponseEntity<?> report(@PathVariable("boardId") Long boardId, @RequestBody ReportRequestDTO requestDTO) {
        /* 현재 인증된 사용자의 인증 토큰을 가져온다.*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(reportService.reportBoard(authentication, boardId, requestDTO,"일반신고"));
    }
}

