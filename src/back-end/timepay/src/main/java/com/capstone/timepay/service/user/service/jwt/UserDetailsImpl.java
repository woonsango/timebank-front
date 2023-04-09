package com.capstone.timepay.service.user.service.jwt;

import com.capstone.timepay.domain.user.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailsImpl implements UserDetails {

    private Long uid;
    private String profileImage;
    private String email;
    private String password;
    private String role;

    private String nickname;



    @Builder
    public UserDetailsImpl(User user) {
        this.uid = user.getUid();
        this.email = user.getEmail();
        this.password = user.getEmail(); // 테스트
        this.nickname = user.getNickname();
        this.role = "ON";
        this.profileImage = user.getUserProfile().getImageUrl();

    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthority = new ArrayList<>();
        grantedAuthority.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return getRole().toString();
            }
        });

        return grantedAuthority;
    }
    @Override
    public String getPassword(){ return password;}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}