package com.capstone.timepay.domain.freeBoardComment;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.freeCommentReport.FreeCommentReport;
import com.capstone.timepay.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class FreeBoardComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long f_commentId;

    @Column // (nullable = false)
    private String content;
    private boolean isHidden;

    @OneToMany(mappedBy = "freeBoardComment", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FreeCommentReport> freeCommentReports = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="f_boardId")
    private FreeBoard freeBoard;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public void updateIsHidden(boolean isHidden){
        this.isHidden = isHidden;
    }

}
