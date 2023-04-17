package com.capstone.timepay.domain.user;

import com.capstone.timepay.domain.BaseTimeEntity;

import com.capstone.timepay.domain.dealBoardReport.DealBoardReport;
import com.capstone.timepay.domain.dealCommentReport.DealCommentReport;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import com.capstone.timepay.domain.freeBoardReport.FreeBoardReport;
import com.capstone.timepay.domain.freeCommentReport.FreeCommentReport;
import com.capstone.timepay.domain.freeRegister.FreeRegister;
import com.capstone.timepay.domain.inquiry.Inquiry;
import com.capstone.timepay.domain.userProfile.UserProfile;
import com.capstone.timepay.domain.userToken.UserToken;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class User extends BaseTimeEntity {

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
    private String encodedPassword;
    private boolean isBanned;
    private boolean isSignUp;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Inquiry> inquiries = new ArrayList<>();

//    @OneToMany(mappedBy = "user", orphanRemoval = true)
//    private List<DealBoardComment> dealBoardComments = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<DealRegister> dealRegisters = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<DealBoardReport> dealBoardReports = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<FreeBoardReport> freeBoardReports = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<DealCommentReport> dealCommentReports = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<FreeCommentReport> freeCommentReports = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<FreeBoardComment> freeBoardComments = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<FreeRegister> freeRegisters = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "profile_id")
    private UserProfile userProfile;

    @OneToOne
    @JoinColumn(name = "token_id")
    private UserToken userToken;


    public void updateName(String name) {
        this.name = name;
    }
    public void updateBirth(LocalDateTime birth) {
        this.birthday = birth;
    }
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
    public void updateLocation(String region) {
        this.location = region;
    }

    public void registerBlacklist() {
        this.isBanned = true;
    }

}
