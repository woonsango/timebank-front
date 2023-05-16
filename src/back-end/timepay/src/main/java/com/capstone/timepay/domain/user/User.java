package com.capstone.timepay.domain.user;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.certification.Certification;
import com.capstone.timepay.domain.dealBoardComment.DealBoardComment;
import com.capstone.timepay.domain.dealBoardReport.DealBoardReport;
import com.capstone.timepay.domain.dealCommentReport.DealCommentReport;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.freeBoardComment.FreeBoardComment;
import com.capstone.timepay.domain.freeBoardReport.FreeBoardReport;
import com.capstone.timepay.domain.freeCommentReport.FreeCommentReport;
import com.capstone.timepay.domain.freeRegister.FreeRegister;
import com.capstone.timepay.domain.inquiry.Inquiry;
import com.capstone.timepay.domain.organization.Organization;
import com.capstone.timepay.domain.userProfile.UserProfile;
import com.capstone.timepay.domain.userToken.UserToken;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String bookmark;
    private boolean isBanned;
    private boolean isSignUp;
    private int totalVolunteerTime;

    @Column(unique = true)
    private String deviceToken;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Inquiry> inquiries = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<DealBoardComment> dealBoardComments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<DealRegister> dealRegisters = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Certification> certifications = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<DealBoardReport> dealBoardReports = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<FreeBoardReport> freeBoardReports = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<DealCommentReport> dealCommentReports = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<FreeCommentReport> freeCommentReports = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<FreeBoardComment> freeBoardComments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<FreeRegister> freeRegisters = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "profile_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserProfile userProfile;

    @OneToOne
    @JoinColumn(name = "token_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserToken userToken;

    @OneToOne
    @JoinColumn(name = "organization_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Organization organization;


    public void updateName(String name) {
        this.name = name;
    }
    public void updatePhone(String phone) {
        this.phone = phone;
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
    public void updateTotalVolunteerTime(int totalVolunteerTime) {
        this.totalVolunteerTime += totalVolunteerTime;
    }

    public void registerBlacklist() {
        this.isBanned = true;
    }

}
