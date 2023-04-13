package com.capstone.timepay.domain.user;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>  findByUid(Long uid);

    Optional<User> findByEmail(String eamil);
    Optional<User> findByName(String name);
    
    Page<User> findAllByName(Pageable pageable, String query);

    Page<User> findAllByNickname(Pageable pageable, String query);

    Page<User> findAllByEmail(Pageable pageable, String query);

    Page<User> findAll(Pageable pageable);

    User findByName(String writer);

}
