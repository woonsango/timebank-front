package com.capstone.timepay.domain.inquiryAnswer;

import com.capstone.timepay.domain.inquiry.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InquiryAnswerRepository extends JpaRepository<InquiryAnswer,Long> {
    Optional<List<InquiryAnswer>> findAllByInquiry(Inquiry inquiry);
}
