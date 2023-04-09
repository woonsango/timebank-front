package com.capstone.timepay.domain.signUpUser;

import com.capstone.timepay.domain.BaseTimeEntity;

import com.capstone.timepay.domain.userProfile.UserProfile;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class SignUpUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column
    private String name;
    private String sex;
    private LocalDateTime birthday; // 카카오 API에서 String으로 제공함
    private String location;
    private String phone;
    private String nickname;
    private String email;
    private Long uid;
    private boolean isBanned;

    @OneToOne
    @JoinColumn(name = "profile_id")
    private UserProfile userProfile;


}
