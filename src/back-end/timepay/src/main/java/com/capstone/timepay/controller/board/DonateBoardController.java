package com.capstone.timepay.controller.board;

import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.service.board.dto.DealBoardDTO;
import com.capstone.timepay.service.board.dto.DonateBoardDTO;
import com.capstone.timepay.service.board.service.DonateBoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/api/donate")
@RequiredArgsConstructor
@RestController
public class DonateBoardController {

    private final UserRepository userRepository;
    private final DonateBoardService donateBoardService;

    // 기부하기 게시글 작성
    @PostMapping("/write")
    @ApiOperation(value = "기부하기 게시글 작성")
    public ResponseEntity donateWrite(@RequestBody DonateBoardDTO donateBoardDTO,
                                      Principal principal)
    {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> {
            return new IllegalArgumentException("해당 유저는 존재하지 않습니다");
        });
        // 현재 유저가 기관유저가 아닐경우 작성 권한이 없음
        if (user.getOrganization() == null)
        {
            return new ResponseEntity("개인 유저는 게시글을 작성할 수 없습니다.", HttpStatus.OK);
        }
        return new ResponseEntity(donateBoardService.donateWrite(donateBoardDTO), HttpStatus.OK);
    }

    @GetMapping("")
    @ApiOperation(value = "작성한 기부 게시판 모두 보기")
    public ResponseEntity<Page<DonateBoardDTO>> getDonates(
            @RequestParam(value = "pagingIndex", defaultValue = "0") int pagingIndex,
            @RequestParam(value = "pagingSize", defaultValue = "10") int pagingSize)
    {
        Page<DonateBoardDTO> paging = donateBoardService.getDonateBoards(pagingIndex, pagingSize);
        if (paging.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(paging, HttpStatus.OK);
    }

    @GetMapping("/{boardId}")
    @ApiOperation(value = "작성한 기부 게시판 하나 보기")
    public DonateBoardDTO getDonate(@PathVariable("boardId") Long boardId)
    {
        return donateBoardService.getDonateBoard(boardId);
    }
}