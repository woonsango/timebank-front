package com.capstone.timepay.controller.board;

import com.capstone.timepay.domain.annotation.Response;
import com.capstone.timepay.domain.freeBoard.FreeBoardRepository;
import com.capstone.timepay.service.board.dto.FreeBoardDTO;
import com.capstone.timepay.service.board.service.FreeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class FreeBoardController {

    private final FreeBoardService freeBoardService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/freeBoard")
    public Response<?> getBoards()
    {
        return new Response("SUCCESS", "전체 게시판 조회", freeBoardService.getBoards());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/freeBoard/{id}")
    public Response<?> getBoard(@PathVariable("id") Long id)
    {
        return new Response("SUCCESS", "개별 게시판 조회", freeBoardService.getBoard(id));
    }

    // 숨김처리 안된 게시물 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/freeBoard/good")
    public Response<?> getGoodBoards()
    {
        return new Response("SUCCESS", "숨김처리 안된 게시판 조회", freeBoardService.getGoodBoard());
    }


    // 숨김처리된 게시물 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/freeBoard/bad")
    public Response<?> getBadBoards()
    {
        return new Response("SUCCESS", "숨김처리 게시판 조회", freeBoardService.getBadBoard());
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/freeBoard/write")
    public Response<?> write(@RequestBody FreeBoardDTO freeBoardDTO)
    {
        return new Response("SUCCESS", "게시글 작성", freeBoardService.write(freeBoardDTO));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/freeBoard/update/{id}")
    public Response<?> update(@RequestBody FreeBoardDTO freeBoardDTO, @PathVariable("id") Long id)
    {
        return new Response("SUCCESS", "게시글 수정", freeBoardService.update(id, freeBoardDTO));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping ("/freeBoard/delete/{id}")
    public Response<?> delete(@PathVariable("id") Long id)
    {
        freeBoardService.delete(id);
        return new Response("SUCCESS", "게시글 삭제", null);
    }

}
