package com.capstone.timepay.domain.dealRegister;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class DealRegister extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long d_registerId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="deal_board_id")
    private DealBoard dealBoard;
}
