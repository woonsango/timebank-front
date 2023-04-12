package com.capstone.timepay.domain.signUpUser;

import com.capstone.timepay.domain.BaseTimeEntity;

import com.capstone.timepay.domain.userProfile.UserProfile;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    private String encodedPassword;
    private boolean isBanned;

    private String roles;


    @ManyToOne
    @JoinColumn(name = "profile_id")
    private UserProfile userProfile;

}
