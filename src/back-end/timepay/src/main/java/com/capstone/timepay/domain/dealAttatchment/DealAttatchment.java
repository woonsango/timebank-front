package com.capstone.timepay.domain.dealAttatchment;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.dealBoard.DealBoard;
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
public class DealAttatchment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long d_attatchmentId;

    @Column
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name="deal_board_id")
    private DealBoard dealBoard;
}
