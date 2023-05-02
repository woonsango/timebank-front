package com.capstone.timepay.controller.admin;

import com.capstone.timepay.controller.admin.response.category.CategoryResponse;
import com.capstone.timepay.service.admin.CategoryManageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/categories")
public class CategoryManageController {

    private final CategoryManageService categoryManageService;

    @ApiOperation(value = "전체 카테고리 항목 조회")
    @GetMapping("/main")
    public ResponseEntity<?> main(@RequestParam(defaultValue = "0") int pageIndex,
                                  @RequestParam(defaultValue = "50") int pageSize){

        Page<CategoryResponse> responses = categoryManageService.showAllCategories(pageIndex, pageSize);

        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "카테고리 항목 추가")
    @PostMapping("/create")
    public ResponseEntity<?> createNewCategory(@RequestParam String categoryName, @RequestParam String boardType){

        categoryManageService.createNewCategory(categoryName,boardType);

        return ResponseEntity.ok("추가되었습니다.");
    }

    @ApiOperation(value = "카테고리 항목 수정")
    @PatchMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestParam Long categoryId, @RequestParam String query){

        categoryManageService.updateCategory(categoryId, query);

        return ResponseEntity.ok("수정되었습니다.");
    }

}
