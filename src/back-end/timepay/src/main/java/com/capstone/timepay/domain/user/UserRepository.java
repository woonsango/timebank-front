package com.capstone.timepay.domain.user;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>  findByUid(Long uid);

    Optional<User> findByEmail(String eamil);
    Optional<User> findByName(String name);
    
    Optional<List<User>> findAllByName(String query);

    Optional<List<User>> findAllByNickname(String query);

    Optional<List<User>> findAllByEmail(String query);
}
