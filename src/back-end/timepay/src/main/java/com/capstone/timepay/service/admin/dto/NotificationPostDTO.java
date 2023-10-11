package com.capstone.timepay.service.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationPostDTO {

    private String title;
    private String imageUrl;
    private boolean isNotice;
    private String content;
    private String state;

}
