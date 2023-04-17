package com.capstone.timepay.controller.board;

import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.service.board.dto.DealBoardDTO;
import com.capstone.timepay.service.board.service.DealBoardService;
import com.capstone.timepay.service.board.service.DealRegisterService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/deal-boards")
public class DealBoardController
{
    private final DealBoardService dealBoardService;
    private final DealRegisterService dealRegisterService;

    @ApiOperation(value = "거래게시판 모든 게시판 불러오기")
    @GetMapping("")
    public ResponseEntity<Page<DealBoardDTO>> getBoards(
            @RequestParam(value = "pagingIndex", defaultValue = "0") int pagingIndex,
            @RequestParam(value = "pagingSize", defaultValue = "50") int pagingSize)
    {
        Page<DealBoardDTO> paging = dealBoardService.getDealBoards(pagingIndex, pagingSize);
        if (paging.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(paging, HttpStatus.OK);
    }

    @ApiOperation(value = "도움주기 게시판 불러오기")
    @GetMapping("/helper")
    public ResponseEntity<Page<DealBoardDTO>> getHelperBoards(
            @RequestParam(value = "pagingIndex", defaultValue = "0") int pagingIndex,
            @RequestParam(value = "pagingSize", defaultValue = "50") int pagingSize)
    {
        Page<DealBoardDTO> paging = dealBoardService.getHelperDealBoard(pagingIndex, pagingSize);
        if (paging.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(paging, HttpStatus.OK);
    }

    @ApiOperation(value = "도움받기 게시판 불러오기")
    @GetMapping("/help")
    public ResponseEntity<Page<DealBoardDTO>> getHelpBoards(
            @RequestParam(value = "pagingIndex", defaultValue = "0") int pagingIndex,
            @RequestParam(value = "pagingSize", defaultValue = "50") int pagingSize)
    {
        Page<DealBoardDTO> paging = dealBoardService.getHelpDealBoard(pagingIndex, pagingSize);
        if (paging.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(paging, HttpStatus.OK);
    }

    @ApiOperation(value = "거래게시판 개별 게시판 불러오기")
    @GetMapping("/{id}")
    public ResponseEntity<DealBoardDTO> getBoard(@PathVariable("id") Long id)
    {
        return new ResponseEntity(dealBoardService.getDealBoard(id), HttpStatus.OK);
    }

    @ApiOperation(value = "거래게시판 도움주기 게시글 작성")
    @PostMapping("/write/helper")
    public ResponseEntity helperWrite(@RequestBody DealBoardDTO dealBoardDTO, Principal principal)
    {
        return new ResponseEntity(dealBoardService.write(dealBoardDTO, principal.getName(), "helper"), HttpStatus.OK);
    }

    @ApiOperation(value = "거래게시판 도움받기 게시글 작성")
    @PostMapping("/write/help")
    public ResponseEntity getHelpWrite(@RequestBody DealBoardDTO dealBoardDTO, Principal principal)
    {
        return new ResponseEntity(dealBoardService.write(dealBoardDTO, principal.getName(), "help"), HttpStatus.OK);
    }

    @ApiOperation(value = "거래게시판 게시글 수정")
    @PutMapping("/update/{id}")
    public Map<String, Object> update(@RequestBody DealBoardDTO dealBoardDTO,
                                      @PathVariable("id") Long id,
                                      Principal principal)
    {
        Map<String, Object> updateMap = new HashMap<>();
        DealBoard dealBoard = dealBoardService.getId(id);
        String boardEmail = dealRegisterService.getEmail(id);

        if (dealBoard == null)
        {
            updateMap.put("success", false);
            updateMap.put("message", "해당 게시글을 찾을 수 없습니다");
            return updateMap;
        }

        if (!principal.getName().equals(boardEmail))
        {
            updateMap.put("success", false);
            updateMap.put("message", "수정 권한이 없습니다");
            return updateMap;
        }

        dealBoardService.update(id, dealBoardDTO);
        updateMap.put("success", true);
        updateMap.put("dealBoard", dealBoard);
        return updateMap;
    }

    @ApiOperation(value = "거래게시판 게시글 삭제")
    @DeleteMapping ("/delete/{id}")
    public Map<String, Object> delete(@RequestBody DealBoardDTO dealBoardDTO,
                                      @PathVariable("id") Long id,
                                      Principal principal)
    {
        Map<String, Object> deleteMap = new HashMap<>();
        String boardEmail = dealRegisterService.getEmail(id);

        DealBoard dealBoard = dealBoardService.getId(id);
        if (dealBoard == null)
        {
            deleteMap.put("success", false);
            deleteMap.put("message", "해당 게시글을 찾을 수 없습니다");
            return deleteMap;
        }

        if (!principal.getName().equals(boardEmail))
        {
            deleteMap.put("success", false);
            deleteMap.put("message", "삭제 권한이 없습니다");
            return deleteMap;
        }

        dealBoardService.delete(id);
        deleteMap.put("success", true);
        return deleteMap;
    }

    // == 글 작성 후 로직 == //
    @ApiOperation(value = "모집중에서 모집완료로 변경시키는 컨트롤러")
    @PutMapping("/{boardId}/start")
    public Map<String, Object> readyToStart(@RequestBody DealBoardDTO dealBoardDTO,
                                            @PathVariable("boardId") Long boardId,
                                            Principal principal)
    {
        Map<String, Object> resultMap = new HashMap<>();
        DealBoard dealBoard  = dealBoardService.getId(boardId);
        String boardEmail = dealRegisterService.getEmail(boardId);

        if (dealBoard == null)
        {
            resultMap.put("success", false);
            resultMap.put("message", "해당 게시글을 찾을 수 없습니다");
            return resultMap;
        }

        if (!principal.getName().equals(boardEmail))
        {
            resultMap.put("success", false);
            resultMap.put("message", "상태를 수정할 권한이 없습니다");
            return resultMap;
        }

        dealBoardService.modifyStatus(boardId, dealBoardDTO);
        resultMap.put("success", true);
        return resultMap;
    }
}