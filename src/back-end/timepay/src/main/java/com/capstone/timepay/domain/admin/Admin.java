package com.capstone.timepay.domain.admin;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.inquiryAnswer.InquiryAnswer;
import com.capstone.timepay.domain.notification.Notification;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Admin extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;   // 식별자

    @Column
    private String adminName;   // 로그인 할 때 쓰는 아이디
    private String password;
    private String name;    // 관리자 이름
    private String email;
    private String phone;
    private boolean isFirst;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    @OneToMany(mappedBy = "admin", orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "admin", orphanRemoval = true)
    private List<InquiryAnswer> inquiryAnswers = new ArrayList<>();
    
    public Admin updatePassword(String password) {
        this.password = password;
        return this;
    }

    public Admin updateFirst(boolean isFirst) {
        this.isFirst = isFirst;
        return this;
    }
}
