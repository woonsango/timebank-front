package com.capstone.timepay.domain.userProfile;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class UserProfile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @Column
    private int boardCnt;
    private int commentCnt;
    private String imageUrl;
    private String introduction;
    private int timepay;

}
