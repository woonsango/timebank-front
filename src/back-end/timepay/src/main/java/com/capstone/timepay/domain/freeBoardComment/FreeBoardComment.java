package com.capstone.timepay.domain.freeBoardComment;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.comment.Comment;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.freeCommentReport.FreeCommentReport;
import com.capstone.timepay.domain.user.TestUser;
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
public class FreeBoardComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long freeBoardCommentId;

    @Column // (nullable = false)
    private String content;

    @OneToMany(mappedBy = "freeBoardComment", orphanRemoval = true)
    private List<FreeCommentReport> freeCommentReports = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="free_board_id")
    private FreeBoard freeBoard;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}
