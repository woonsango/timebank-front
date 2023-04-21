package com.capstone.timepay.domain.organization;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization,Long> {
    Optional<Organization> findByAccount(String id);

    void deleteByAccount(String account);
}
