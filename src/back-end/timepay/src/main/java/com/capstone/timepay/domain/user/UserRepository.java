package com.capstone.timepay.domain.user;

import com.capstone.timepay.domain.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUid(Long uid);
    Optional<User> findByName(String name);
    
    Optional<List<User>> findAllByName(String query);

    Optional<List<User>> findAllByNickname(String query);

    Optional<List<User>> findAllByEmail(String query);
}
