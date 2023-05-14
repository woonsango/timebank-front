package com.capstone.timepay.controller.user.request;

import lombok.Data;


@Data
public class BookmarkDTO {
    private Long id;
    private String bookmark;

    public BookmarkDTO(Long id, String bookmark){
        this.id = id;
        this.bookmark = bookmark;
    }
}
