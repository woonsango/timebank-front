package com.capstone.timepay.domain.dealBoardComment;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealCommentReport.DealCommentReport;
import com.capstone.timepay.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class DealBoardComment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long d_commentId;

    @Column(nullable = false)
    private String content;
    private boolean isApplied;
    private boolean isAdopted;
    // 비공개여부
    private boolean isHidden;

    @JsonIgnore
    @OneToMany(mappedBy = "dealBoardComment", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DealCommentReport> dealCommentReports = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="d_boardId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DealBoard dealBoard;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public void updateIsHidden(boolean isHidden){
        this.isHidden = isHidden;
    }


}
