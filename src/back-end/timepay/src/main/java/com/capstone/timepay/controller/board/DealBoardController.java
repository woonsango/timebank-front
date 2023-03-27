package com.capstone.timepay.controller.board;

import com.capstone.timepay.controller.board.annotation.Response;
import com.capstone.timepay.service.board.dto.DealBoardDTO;
import com.capstone.timepay.service.board.service.DealBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/deal-boards")
public class DealBoardController
{
    private final DealBoardService dealBoardService;

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
}
