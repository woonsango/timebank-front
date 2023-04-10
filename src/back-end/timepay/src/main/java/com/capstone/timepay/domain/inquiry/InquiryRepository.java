package com.capstone.timepay.domain.inquiry;

//import org.springframework.data.domain.Page;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InquiryRepository extends JpaRepository<Inquiry,Long> {
    Page<Inquiry> findAllByCategory(String category, Pageable pageable);

    Page<Inquiry> findAllByState(String state, Pageable pageable);

    Page<Inquiry> findAllByCategoryAndState(String category, String state, Pageable pageable);

    Page<Inquiry> findAll(Pageable pageable);
}
