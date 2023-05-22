package com.capstone.timepay.service.admin;

import com.capstone.timepay.controller.admin.response.organization.OrganizationMain;
import com.capstone.timepay.domain.organization.Organization;
import com.capstone.timepay.domain.organization.OrganizationRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminOrgaManageService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    public Page<OrganizationMain> showAllUserList(int pageIndex, int pageSize) {

        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("userId"));
        Page<User> pages = userRepository.findAllByOrganizationIsNotNull(pageable);

        return convertResponsePages(pages);
    }
    public Page<OrganizationMain> convertResponsePages(Page<User> pages){
        Page<OrganizationMain> pageResponses = pages.map(new Function<User, OrganizationMain>() {
            @Override
            public OrganizationMain apply(User user) {
                return OrganizationMain.builder()
                        .userId(user.getUserId())
                        .id(user.getOrganization().getAccount())
                        .organizationName(user.getOrganization().getOrganizationName())
                        .imageUrl(user.getOrganization().getImageUrl())
                        .businessNumber(user.getOrganization().getBusinessCode())
                        .managerName(user.getName())
                        .managerPhone(user.getPhone())
                        .certificationUrl(user.getOrganization().getCertificationUrl())
                        .authority(user.getOrganization().getAuthority())
                        .employeeNum(user.getOrganization().getEmployeeNum())
                        .timepay(user.getOrganization().getTimepay())
                        .build();
            }
        });

        return pageResponses;
    }

    public void giveAuthority(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 계정입니다."));
        if(Objects.isNull(user.getOrganization())) throw new IllegalArgumentException("기관 회원이 아닙니다.");

        user.getOrganization().updateAuthority("volunteer");
        userRepository.save(user);  // organization도 같이 저장해야되는지 검토 필요

    }

    public Page<OrganizationMain> showAllUserListBySearch(String query, String value, String volunteer) {

        List<OrganizationMain> responses = new ArrayList<>();

        if(Objects.isNull(query) && Objects.isNull(value) && !Objects.isNull(volunteer)){
            if(volunteer.equals("y")){
                List<Organization> organizations = organizationRepository.findAllByAuthority("volunteer");
                if(ObjectUtils.isEmpty(organizations)) return new PageImpl<>(new ArrayList<>());
                List<OrganizationMain> results = new ArrayList<>();
                for(Organization element : organizations){
                    User user = userRepository.findByOrganization(element).get();
                    results.add(convertUserToResponse(user));
                }
                responses = results;
            }
            else{
                List<User> users = userRepository.findAllByOrganizationIsNotNull();
                if(ObjectUtils.isEmpty(users)) return new PageImpl<>(new ArrayList<>());
                responses.addAll(convertUserToResponses(users));
            }
        }
        else if(query.equals("business") && !Objects.isNull(query) && !Objects.isNull(value)){
            if(volunteer.equals("y")){
                List<Organization> organizations = organizationRepository.findAllByBusinessCodeContainsAndAuthority(value,"volunteer");
                if(ObjectUtils.isEmpty(organizations)) return new PageImpl<>(new ArrayList<>());
                List<OrganizationMain> results = new ArrayList<>();
                for(Organization element : organizations){
                    User user = userRepository.findByOrganization(element).get();
                    results.add(convertUserToResponse(user));
                }
                responses = results;
            }
            else{
                List<Organization> organizations = organizationRepository.findAllByBusinessCodeContains(value);
                if(ObjectUtils.isEmpty(organizations)) return new PageImpl<>(new ArrayList<>());
                List<OrganizationMain> results = new ArrayList<>();
                for(Organization element : organizations){
                    User user = userRepository.findByOrganization(element).get();
                    results.add(convertUserToResponse(user));
                }
                responses = results;
            }
        }
        else if(query.equals("user") && !Objects.isNull(query) && !Objects.isNull(value)) {
            User user = userRepository.findById(Long.parseLong(value)).orElse(null);
            if(Objects.isNull(user)) return new PageImpl<>(new ArrayList<>());
            responses.add(convertUserToResponse(user)); 
        }
        else if(query.equals("name") && !Objects.isNull(query) && !Objects.isNull(value)){
            if(volunteer.equals("y")){
                List<Organization> organizations = organizationRepository.findAllByAuthority("volunteer");
                if(ObjectUtils.isEmpty(organizations)) return new PageImpl<>(new ArrayList<>());
                List<OrganizationMain> results = new ArrayList<>();
                for(Organization element : organizations){
                    List<User> users = userRepository.findAllByOrganizationAndNameContains(element,value);
                    results.addAll(convertUserToResponses(users));
                }
                responses = results;
            }
            else{
                List<User> users = userRepository.findAllByNameContainsAndOrganizationIsNotNull(value);
                if(ObjectUtils.isEmpty(users)) return new PageImpl<>(new ArrayList<>());

                responses.addAll(convertUserToResponses(users));
            }
        }
        return new PageImpl<>(responses);

    }

    private List<OrganizationMain> convertUserToResponses(List<User> users) {
        List<OrganizationMain> responses = new ArrayList<>();
        for(User user : users){
            OrganizationMain organization = OrganizationMain.builder()
                    .userId(user.getUserId())
                    .id(user.getOrganization().getAccount())
                    .organizationName(user.getOrganization().getOrganizationName())
                    .imageUrl(user.getOrganization().getImageUrl())
                    .businessNumber(user.getOrganization().getBusinessCode())
                    .managerName(user.getName())
                    .managerPhone(user.getPhone())
                    .certificationUrl(user.getOrganization().getCertificationUrl())
                    .authority(user.getOrganization().getAuthority())
                    .employeeNum(user.getOrganization().getEmployeeNum())
                    .timepay(user.getOrganization().getTimepay())
                    .build();
            responses.add(organization);
        }
        return responses;
    }

    public OrganizationMain convertUserToResponse(User user){
        return OrganizationMain.builder()
                .userId(user.getUserId())
                .id(user.getOrganization().getAccount())
                .organizationName(user.getOrganization().getOrganizationName())
                .imageUrl(user.getOrganization().getImageUrl())
                .businessNumber(user.getOrganization().getBusinessCode())
                .managerName(user.getName())
                .managerPhone(user.getPhone())
                .certificationUrl(user.getOrganization().getCertificationUrl())
                .authority(user.getOrganization().getAuthority())
                .employeeNum(user.getOrganization().getEmployeeNum())
                .timepay(user.getOrganization().getTimepay())
                .build();
    }

    public void givePenalty(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 계정입니다."));
        if(Objects.isNull(user.getOrganization())) throw new IllegalArgumentException("기관 회원이 아닙니다.");

        user.registerBlacklist();
        userRepository.save(user);
    }
}
