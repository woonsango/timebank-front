package com.capstone.timepay.domain.inquiry;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InquiryRepository extends JpaRepository<Inquiry,Long> {
    Optional<List<Inquiry>> findAllByCategory(String category);

    Optional<List<Inquiry>> findAllByState(String state);

    Optional<List<Inquiry>> findAllByCategoryAndState(String category, String state);
}
