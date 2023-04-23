package com.capstone.timepay.controller.user.response;
import com.capstone.timepay.domain.board.Board;
import com.capstone.timepay.domain.comment.Comment;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import com.capstone.timepay.domain.freeRegister.FreeRegister;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;


@Data
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetResponseDTO {
    private Long id; // 고유식별번호
    private String imageUrl; // 프로필 이미지

    private String nickName; // 닉네임

    private String location;

    private String introduction;

    private int timePay; // 타임페이, userProfile에서 가져옴

    private Page<Board> boards;
    private Page<Comment> comments;

    /* 아래는 타유저 프로필 정보 조회를 위해 생성된 데이터 */
    private List<FreeRegister> freeRegister;
    private List<DealRegister> dealRegister;
    private List<FreeBoardComment> freeBoardComment;
    private List<DealBoardComment> dealBoardComment;


    /* 타유저 프로필 정보 조회를 위한 DTO */
    public GetResponseDTO(Long id, String imageUrl, String nickName, String location, String intoroduction, int timePay, List<FreeRegister> freeRegister, List<DealRegister> dealRegister, List<FreeBoardComment> freeBoardComment, List<DealBoardComment> dealBoardComment){
        this.id = id;
        this.imageUrl = imageUrl;
        this.nickName = nickName;
        this.location = location;
        this.introduction = intoroduction;
        this.timePay = timePay;
        this.freeRegister = freeRegister;
        this.dealRegister = dealRegister;
        this.freeBoardComment = freeBoardComment;
        this.dealBoardComment = dealBoardComment;
    }

    public GetResponseDTO(Long id, String imageUrl, String nickName, String location, String intoroduction, int timePay, Page<Board> boards, Page<Comment> comments){
        this.id = id;
        this.imageUrl = imageUrl;
        this.nickName = nickName;
        this.location = location;
        this.introduction = intoroduction;
        this.timePay = timePay;
        this.boards = boards;
        this.comments = comments;
    }
}