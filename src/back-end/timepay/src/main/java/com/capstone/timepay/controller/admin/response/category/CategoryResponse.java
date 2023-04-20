package com.capstone.timepay.controller.admin.response.category;

import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Long categoryId;
    private String boardType;
    private String categoryName;
    private String useYn;

}
