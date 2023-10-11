package com.capstone.timepay.service.organization;

import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.domain.admin.AdminRepository;
import com.capstone.timepay.domain.organization.Organization;
import com.capstone.timepay.domain.organization.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class OrganizationDetailService implements UserDetailsService {

    private final OrganizationRepository organizationRepository;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Optional<Organization> organization = organizationRepository.findByAccount(account);

        if (!organization.isPresent()) {
            throw new UsernameNotFoundException("User not found with ID: " + account);
        }

        return new User(
                organization.get().getAccount(), organization.get().getPw(), organization.get().getAuthorities());
    }

    public Organization getOrganization(String account) {
        return organizationRepository.findByAccount(account).get();
    }
}
