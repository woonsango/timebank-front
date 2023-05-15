package com.capstone.timepay.controller.board;

import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoard.DealBoardSearch;
import com.capstone.timepay.service.board.service.DealBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/deal-boards/search")
@RequiredArgsConstructor
public class DealBoardSearchController {

    private final DealBoardService dealBoardService;

    @GetMapping
    public ResponseEntity<Page<DealBoard>> searchDealBoards(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "sortType", required = false) String sortType,
            @RequestParam(value = "startTime", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS") LocalDateTime startTime,
            @RequestParam(value = "endTime", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS") LocalDateTime endTime,
            @RequestParam(value = "volunteer", required = false) boolean volunteer,
            @RequestParam(value = "pagingSize", defaultValue = "10") int perPage,
            @RequestParam(value = "pagingIndex", defaultValue = "0") int curPage
    ) {
        Specification<DealBoard> spec = DealBoardSearch.withTitle(title)
                .and(DealBoardSearch.withContent(content))
                .and(DealBoardSearch.withType(type))
                .and(DealBoardSearch.withCategory(category))
                .and(DealBoardSearch.withStartTime(startTime))
                .and(DealBoardSearch.withEndTime(endTime))
                .and(DealBoardSearch.withVolunteer(volunteer));

        Pageable pageable = PageRequest.of(curPage, perPage, getSort(sortType));
        Page<DealBoard> dealBoards = dealBoardService.search(spec, pageable);

        return ResponseEntity.ok(dealBoards);
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
}
