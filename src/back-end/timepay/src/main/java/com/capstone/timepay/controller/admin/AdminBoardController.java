package com.capstone.timepay.controller.admin;

import com.capstone.timepay.service.admin.AdminBoardService;
import com.capstone.timepay.service.admin.dto.AdminBoardDTO;
import com.capstone.timepay.service.admin.dto.AdminBoardHiddenDto;
import com.capstone.timepay.service.admin.dto.AdminBoardStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admins/boards")
public class AdminBoardController {

    private final AdminBoardService boardService;

    @GetMapping("")
    public ResponseEntity<Page<AdminBoardDTO>> getAllBoards(
            @RequestParam(value = "pagingIndex", defaultValue = "0") int pagingIndex,
            @RequestParam(value = "pagingSize", defaultValue = "50") int pagingSize) {
        Page<AdminBoardDTO> page = boardService.getAllBoards(pagingIndex, pagingSize);
        if (page.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping("/hidden")
    public ResponseEntity<?> setHidden(@RequestBody AdminBoardHiddenDto dto) {
        boolean result = boardService.setHidden(dto.getIds());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/status")
    public ResponseEntity<?> setStatus(@RequestBody AdminBoardStatusDto dto) {
        boolean result = boardService.setStatus(dto.getIds(), dto.getStatus());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
