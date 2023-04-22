package com.capstone.timepay.domain.freeBoardReport;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class FreeBoardReport extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fb_reportId;

    @Column
    private String content;
    private String process;

    @ManyToOne
    @JoinColumn(name="f_board_id")
    private FreeBoard freeBoard;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public FreeBoardReport(User user, String content, String process, FreeBoard freeBoard){
        this.user = user;
        this.content = content;
        this.process = process;
        this.freeBoard = freeBoard;
    }
}
