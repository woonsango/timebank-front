package com.capstone.timepay.domain.userToken;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.userProfile.UserProfile;
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
public class UserToken extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    @Column
    private String accessToken;
    private String refrechToken;


}
