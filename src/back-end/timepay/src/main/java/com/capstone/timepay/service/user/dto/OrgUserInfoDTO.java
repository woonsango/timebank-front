package com.capstone.timepay.service.user.dto;


import com.capstone.timepay.controller.admin.response.comment.CommentResponse;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import lombok.*;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgUserInfoDTO {
    private String organizationName;
    private String managerName;
    private String managerPhone;
    private String businessCode;
    private int employeeNum;
    private int timepay;
    private String id;
    private String imageUrl;
    private String certificationUrl;

    private Page<DealBoard> dealBoards;
    private Page<CommentResponse> dealBoardComments;

    public OrgUserInfoDTO(String organizationName, String managerName, String managerPhone, String businessCode,
                          int employeeNum, int timepay, String id, String imageUrl, String certificationUrl){
        this.organizationName = organizationName;
        this.managerName = managerName;
        this.managerPhone = managerPhone;
        this.businessCode = businessCode;
        this.employeeNum = employeeNum;
        this.timepay = timepay;
        this.id = id;
        this.imageUrl = imageUrl;
        this.certificationUrl = certificationUrl;
    }

    public OrgUserInfoDTO(String organizationName, String managerName, String managerPhone, String businessCode,
                          int employeeNum, int timepay, String id, String imageUrl, String certificationUrl,
                          Page<DealBoard> dealBoards){
        this.organizationName = organizationName;
        this.managerName = managerName;
        this.managerPhone = managerPhone;
        this.businessCode = businessCode;
        this.employeeNum = employeeNum;
        this.timepay = timepay;
        this.id = id;
        this.imageUrl = imageUrl;
        this.certificationUrl = certificationUrl;
        this.dealBoards = dealBoards;
    }

}
