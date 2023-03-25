package com.capstone.timepay.domain.admin;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.notification.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Admin extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long adminId;   // 식별자

    @Column
    private String adminName;   // 로그인 할 때 쓰는 아이디
    private String password;
    private String authority;
    private String name;    // 관리자 이름
    private String email;
    private String phone;

    @OneToMany(mappedBy = "admin", orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();
}
