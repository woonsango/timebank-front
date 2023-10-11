package com.capstone.timepay.service.admin;

import com.capstone.timepay.controller.admin.response.category.CategoryResponse;
import com.capstone.timepay.domain.category.Category;
import com.capstone.timepay.domain.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryManageService {

    private final CategoryRepository categoryRepository;

    public Page<CategoryResponse> convertResponsePages(Page<Category> pages){
        Page<CategoryResponse> pageResponses = pages.map(new Function<Category, CategoryResponse>() {
            @Override
            public CategoryResponse apply(Category category) {
                return CategoryResponse.builder()
                        .categoryId(category.getCategoryId())
                        .boardType(category.getBoardType())
                        .categoryName(category.getCategoryName())
                        .useYn(category.getUseYn())
                        .build();
            }
        });

        return pageResponses;
    }
    public Page<CategoryResponse> showAllCategories(int pageIndex, int pageSize) {

        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createdAt").descending());
        Page<Category> pages = categoryRepository.findAll(pageable);

        return convertResponsePages(pages);
    }


    public void createNewCategory(String categoryName, String boardType) {

        if(Objects.isNull(categoryName) || Objects.isNull(boardType)) throw new IllegalArgumentException("잘못된 요청입니다.");
        else{
            Category newCategory = Category.builder()
                    .boardType(boardType)
                    .categoryName(categoryName)
                    .useYn("Y")
                    .build();

            categoryRepository.save(newCategory);
        }

    }

    public void updateCategory(Long categoryId, String query) {
        if(Objects.isNull(categoryId) || Objects.isNull(query)) throw new IllegalArgumentException("잘못된 요청입니다.");
        else{
            Category category = categoryRepository.findById(categoryId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 카테고리입니다."));
            category.updateUseYn(query);
            categoryRepository.save(category);
        }
    }

    public List<CategoryResponse> showPossibleCategories(String type, String useYn) {

        List<Category> categories = categoryRepository.findAllByBoardTypeAndUseYn(type,useYn);

        return convertCategoryList(categories);
    }

    public List<CategoryResponse> convertCategoryList(List<Category> categories){
        return categories.stream()
                .map(category -> CategoryResponse.builder()
                        .categoryId(category.getCategoryId())
                        .boardType(category.getBoardType())
                        .categoryName(category.getCategoryName())
                        .useYn(category.getUseYn())
                        .build())
                .collect(Collectors.toList());
    }
}
