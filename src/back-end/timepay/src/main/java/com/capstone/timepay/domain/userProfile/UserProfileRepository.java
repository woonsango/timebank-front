package com.capstone.timepay.domain.userProfile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
    // public UserProfile findByUid(Long uid);
}
