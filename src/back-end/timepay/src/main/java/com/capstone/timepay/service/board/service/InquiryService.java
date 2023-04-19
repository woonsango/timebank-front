package com.capstone.timepay.service.board.service;

import com.capstone.timepay.domain.inquiry.Inquiry;
import com.capstone.timepay.domain.inquiry.InquiryRepository;
import com.capstone.timepay.service.board.dto.InquiryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    public Inquiry createInquiry(InquiryDTO inquiryDTO) {
        Inquiry inquiry = new Inquiry();
        inquiry.setTitle(inquiryDTO.getTitle());
        inquiry.setCategory(inquiryDTO.getCategory());
        inquiry.setState(inquiryDTO.getState());
        inquiry.setContent(inquiryDTO.getContent());
        return inquiryRepository.save(inquiry);
    }

    public List<Inquiry> getAllInquiries() {
        return inquiryRepository.findAll();
    }

    public Optional<Inquiry> getInquiryById(Long inquiryId) {
        return inquiryRepository.findById(inquiryId);
    }

    public Inquiry updateInquiry(Long inquiryId, InquiryDTO inquiryDTO) {
        Optional<Inquiry> optionalInquiry = inquiryRepository.findById(inquiryId);
        if (optionalInquiry.isPresent()) {
            Inquiry inquiry = optionalInquiry.get();
            inquiry.setTitle(inquiryDTO.getTitle());
            inquiry.setCategory(inquiryDTO.getCategory());
            inquiry.setState(inquiryDTO.getState());
            inquiry.setContent(inquiryDTO.getContent());
            return inquiryRepository.save(inquiry);
        } else {
            return null;
        }
    }

    public void deleteInquiry(Long inquiryId) {
        inquiryRepository.deleteById(inquiryId);
    }
}