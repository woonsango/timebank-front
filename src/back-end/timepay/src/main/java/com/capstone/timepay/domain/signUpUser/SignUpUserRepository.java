package com.capstone.timepay.domain.signUpUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SignUpUserRepository extends JpaRepository<SignUpUser,Long> {
    Optional<SignUpUser>  findByUid(Long uid);

    Optional<SignUpUser> findByEmail(String eamil);
    Optional<SignUpUser> findByName(String name);

    Optional<List<SignUpUser>> findAllByName(String query);

    Optional<List<SignUpUser>> findAllByNickname(String query);

    Optional<List<SignUpUser>> findAllByEmail(String query);
}