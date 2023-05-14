package com.capstone.timepay.domain.inquiry;

//import org.springframework.data.domain.Page;
import com.capstone.timepay.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry,Long> {
    Page<Inquiry> findAllByCategory(String category, Pageable pageable);

    Page<Inquiry> findAllByState(String state, Pageable pageable);

    Page<Inquiry> findAllByCategoryAndState(String category, String state, Pageable pageable);

    Page<Inquiry> findAll(Pageable pageable);

    Page<Inquiry> findByTitleContains(String title, Pageable pageable);

    Page<Inquiry> findAllByUser(User user, Pageable pageable);

    Page<Inquiry> findByUserEmail(String email, Pageable pageable);

    List<Inquiry> findAllByStateAndCategoryAndUser(String state, String category, User user);

    List<Inquiry> findAllByStateAndCategoryAndTitleContains(String state, String category, String title);

    List<Inquiry> findAllByStateAndCategoryAndUserAndTitleContains(String state, String category, User user, String title);

    List<Inquiry> findAllByStateAndCategory(String state, String category);
}
