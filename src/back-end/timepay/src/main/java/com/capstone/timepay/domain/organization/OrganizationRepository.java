package com.capstone.timepay.domain.organization;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization,Long> {
    Optional<Organization> findByAccount(String id);

    void deleteByAccount(String account);

    List<Organization> findAllByBusinessCodeContainsAndAuthority(String value, String volunteer);

    List<Organization> findAllByBusinessCodeContains(String value);

    List<Organization> findAllByAuthority(String volunteer);

    Optional<Organization> findByOrganizationName(String organizationName);
}
