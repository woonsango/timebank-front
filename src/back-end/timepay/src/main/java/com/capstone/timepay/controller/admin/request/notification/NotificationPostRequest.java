package com.capstone.timepay.controller.admin.request.notification;

import com.capstone.timepay.service.admin.dto.NotificationPostDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.modelmapper.ModelMapper;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPostRequest {

    private String title;
    @ApiModelProperty(hidden = true)
    private String imageUrl;
    private Boolean isNotice;
    private String content;
    private String state;

    public NotificationPostDTO toServiceDto() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, NotificationPostDTO.class);
    }
}
