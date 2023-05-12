package com.capstone.timepay.domain.user;


import com.capstone.timepay.domain.organization.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String eamil);
    Optional<User> findByName(String name);
    Optional<User> findByNickname(String nickName);

    Page<User> findAllByName(Pageable pageable, String query);

    Page<User> findAllByNickname(Pageable pageable, String query);

    Page<User> findAllByEmail(Pageable pageable, String query);

    Page<User> findAll(Pageable pageable);


    Optional<User> findByNicknameContains(String nickname);

    Optional<User> findByOrganization(Organization organization);

    Page<User> findAllByOrganizationIsNull(Pageable pageable);

    List<User> findByUserIdAndOrganizationIsNull(Long userId);

    Page<User> findAllByNameAndOrganizationIsNull(Pageable pageable, String value);

    Page<User> findAllByEmailAndOrganizationIsNull(Pageable pageable, String value);

    Page<User> findAllByNicknameAndOrganizationIsNull(Pageable pageable, String value);

    Optional<User> findByNameContains(String value);
}
