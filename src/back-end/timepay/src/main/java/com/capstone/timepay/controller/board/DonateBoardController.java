package com.capstone.timepay.controller.board;

import com.capstone.timepay.domain.organization.Organization;
import com.capstone.timepay.domain.organization.OrganizationRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.service.board.dto.DealBoardDTO;
import com.capstone.timepay.service.board.dto.DonateBoardDTO;
import com.capstone.timepay.service.board.dto.DonateDTO;
import com.capstone.timepay.service.board.service.DonateBoardService;
import com.capstone.timepay.service.organization.OrganizationUserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class DonateBoardController {

    private final UserRepository userRepository;
    private final DonateBoardService donateBoardService;
    private final OrganizationUserService organizationUserService;

    // 기부하기 게시글 작성
    @PostMapping("/organizations/donate/write")
    @ApiOperation(value = "기부하기 게시글 작성")
    public ResponseEntity donateWrite(@RequestBody DonateBoardDTO donateBoardDTO,
                                      Principal principal)
    {
        return new ResponseEntity(donateBoardService.donateWrite(donateBoardDTO, principal), HttpStatus.OK);
    }

    @GetMapping("/donate")
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

    @GetMapping("/donate/{boardId}")
    @ApiOperation(value = "작성한 기부 게시판 하나 보기")
    public DonateBoardDTO getDonate(@PathVariable("boardId") Long boardId)
    {
        return donateBoardService.getDonateBoard(boardId);
    }

    @PutMapping("/organizations/donate/update/{boardId}")
    @ApiOperation(value = "기부게시판 수정하기")
    public DonateBoardDTO updateDonate(@PathVariable("boardId") Long boardId,
                                       @RequestBody DonateBoardDTO donateBoardDTO)
    {
        return donateBoardService.updateDonate(boardId, donateBoardDTO);
    }

    @DeleteMapping("/organizations/donate/delete/{boardId}")
    @ApiOperation(value = "기부게시판 삭제하기")
    public void deleteDonate(@PathVariable("boardId") Long boardId)
    {
        donateBoardService.deleteDonate(boardId);
    }

    @PostMapping("/donate/timepay/{boardId}")
    @ApiOperation(value = "기부게시판에 기부하기")
    public ResponseEntity<String> donateDonate(@PathVariable("boardId") Long boardId,
                             @RequestBody DonateDTO donateDTO,
                             Principal principal)
    {
        donateBoardService.donateDonate(boardId, donateDTO, principal);
        return new ResponseEntity("기부완료 했습니다", HttpStatus.OK);
    }
}