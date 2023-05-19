package com.capstone.timepay.domain.agent;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Agent extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agentNum;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name="agent_user_id")
    private User agent_user;

}
