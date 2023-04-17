package com.capstone.timepay.controller.board;

import com.capstone.timepay.controller.board.annotation.Response;
import com.capstone.timepay.controller.board.request.ReportRequestDTO;
import com.capstone.timepay.service.board.dto.DealBoardDTO;
import com.capstone.timepay.service.board.service.DealBoardService;
import com.capstone.timepay.service.board.service.ReportService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/deal-boards")
public class DealBoardController
{
    private final DealBoardService dealBoardService;
    private final ReportService reportService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public Response<?> getBoards()
    {
        return new Response("SUCCESS", "전체 게시판 조회", dealBoardService.getDealBoards());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Response<?> getBoard(@PathVariable("id") Long id)
    {
        return new Response("SUCCESS", "개별 게시판 조회", dealBoardService.getDealBoard(id));
    }

    // 숨김처리 안된 게시물 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/good")
    public Response<?> getGoodBoards()
    {
        return new Response("SUCCESS", "숨김처리 안된 게시판 조회", dealBoardService.getGoodBoard());
    }


    // 숨김처리된 게시물 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/bad")
    public Response<?> getBadBoards()
    {
        return new Response("SUCCESS", "숨김처리 게시판 조회", dealBoardService.getBadBoard());
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/write")
    public Response<?> write(@RequestBody DealBoardDTO dealBoardDTO)
    {
        return new Response("SUCCESS", "게시글 작성", dealBoardService.write(dealBoardDTO));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update/{id}")
    public Response<?> update(@RequestBody DealBoardDTO dealBoardDTO, @PathVariable("id") Long id)
    {
        return new Response("SUCCESS", "게시글 수정", dealBoardService.update(id, dealBoardDTO));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping ("/delete/{id}")
    public Response<?> delete(@PathVariable("id") Long id)
    {
        dealBoardService.delete(id);
        return new Response("SUCCESS", "게시글 삭제", null);
    }

    @PostMapping("/{boardId}/report")
    @ApiOperation(value = "신고 API", notes = "JWT 토큰으로 유저를 구분하여 신고 DB에 작성합니다.")
    public ResponseEntity<?> report(@PathVariable("boardId") Long boardId, @RequestBody ReportRequestDTO requestDTO) {
        /* 현재 인증된 사용자의 인증 토큰을 가져온다.*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(reportService.reportBoard(authentication, boardId, requestDTO, "거래신고"));

    }
}
