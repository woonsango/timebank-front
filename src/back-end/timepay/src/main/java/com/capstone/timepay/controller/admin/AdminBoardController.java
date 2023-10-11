package com.capstone.timepay.controller.admin;

import com.capstone.timepay.controller.admin.response.board.DealBoardResponse;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardSearch;
import com.capstone.timepay.service.admin.AdminBoardService;
import com.capstone.timepay.service.admin.dto.AdminBoardDTO;
import com.capstone.timepay.service.admin.dto.AdminBoardHiddenDto;
import com.capstone.timepay.service.admin.dto.AdminBoardStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

    private Sort getSort(String sortType) {
        if (sortType == null) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }
        switch (sortType) {
            case "최신순":
                return Sort.by(Sort.Direction.DESC, "createdAt");
            case "과거순":
                return Sort.by(Sort.Direction.ASC, "createdAt");
            default:
                throw new IllegalArgumentException("Invalid sort type: " + sortType);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DealBoardResponse>> searchDealBoards(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String name,
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "sortType", required = false) String sortType,
            @RequestParam(value = "startTime", required = false) LocalDateTime startTime,
            @RequestParam(value = "endTime", required = false) LocalDateTime endTime,
            @RequestParam(value = "volunteer", required = false) boolean volunteer,
            @RequestParam(value = "pagingSize", defaultValue = "10") int perPage,
            @RequestParam(value = "pagingIndex", defaultValue = "0") int curPage
    ) {
        Specification<DealBoard> spec = DealBoardSearch.withTitle(title)
                .and(DealBoardSearch.withName(name))
                .and(DealBoardSearch.withNickname(nickname))
                .and(DealBoardSearch.withType(type))
                .and(DealBoardSearch.withCategory(category))
                .and(DealBoardSearch.withStartTime(startTime))
                .and(DealBoardSearch.withEndTime(endTime))
                .and(DealBoardSearch.withVolunteer(volunteer));

        Pageable pageable = PageRequest.of(curPage, perPage, getSort(sortType));
        Page<DealBoardResponse> dealBoards = boardService.search(spec, pageable);

        return ResponseEntity.ok(dealBoards);
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
