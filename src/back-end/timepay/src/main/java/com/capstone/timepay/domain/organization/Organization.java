package com.capstone.timepay.domain.organization;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.user.User;
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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Builder
public class Organization extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organizationId;

    private String organizationName;
    private String businessCode;
    private int employeeNum;
    private int timepay;
    private String account;
    private String pw;
    private String authority;
    private String imageUrl;
    private String certificationUrl;
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<String> roles = new ArrayList<>();
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public void updateAuthority(String authority){
        this.authority= authority;
    }
    public void updateImageUrl(String imageUrl){
        this.imageUrl= imageUrl;
    }
    public void updateCertificationUrl(String certificationUrl){
        this.certificationUrl= certificationUrl;
    }

}
