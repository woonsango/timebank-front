package com.capstone.timepay.domain.freeAttatchment;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
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
public class FreeAttatchment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long f_attatchmentId;

    @Column
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name="f_board_id")
    private FreeBoard freeBoard;
}
