package com.capstone.timepay.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUid(Long uid);
    
    Optional<List<User>> findAllByName(String query);

    Optional<List<User>> findAllByNickname(String query);

    Optional<List<User>> findAllByEmail(String query);

    Page<User> findAll(Pageable pageable);

    List<User> findByNameContains(String query);

    List<User> findByNicknameContains(String query);

    List<User> findByEmailContains(String query);

    User findByName(String writer);
}
