package com.capstone.timepay.domain.inquiry;

//import org.springframework.data.domain.Page;
import com.capstone.timepay.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry,Long> {
    Page<Inquiry> findAllByCategory(String category, Pageable pageable);

    Page<Inquiry> findAllByState(String state, Pageable pageable);

    Page<Inquiry> findAllByCategoryAndState(String category, String state, Pageable pageable);

    Page<Inquiry> findAll(Pageable pageable);

    Page<Inquiry> findByTitleContains(String title, Pageable pageable);

    Page<Inquiry> findAllByUser(User user, Pageable pageable);
}
