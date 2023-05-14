package com.capstone.timepay.service.board.service;

import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.inquiry.Inquiry;
import com.capstone.timepay.domain.inquiry.InquiryRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.service.board.dto.DealBoardDTO;
import com.capstone.timepay.service.board.dto.InquiryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;

    public InquiryDTO createInquiry(InquiryDTO inquiryDTO, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            return new IllegalArgumentException("해당 유저를 찾을 수 없습니다.");
        });
        Inquiry inquiry = new Inquiry();
        inquiry.setTitle(inquiryDTO.getTitle());
        inquiry.setCategory(inquiryDTO.getCategory());
        inquiry.setState("답변대기");
        inquiry.setContent(inquiryDTO.getContent());
        inquiry.setUser(user);
        inquiryRepository.save(inquiry);

        return InquiryDTO.toInquiryDTO(inquiry);
    }

    @Transactional(readOnly = true)
    public Page<InquiryDTO> getAllInquiries(int pagingIndex, int pagingSize) {
        Pageable pageable = PageRequest.of(pagingIndex, pagingSize);
        Page<Inquiry> inquiries = inquiryRepository.findAll(pageable);
        List<InquiryDTO> inquiryDTOS = inquiries.stream()
                .map(InquiryDTO::toInquiryDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(inquiryDTOS, inquiries.getPageable(), inquiries.getTotalElements());
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
            inquiry.setContent(inquiryDTO.getContent());
            inquiry.setUpdatedAt(LocalDateTime.now());
            return inquiryRepository.save(inquiry);
        } else {
            return null;
        }
    }

    public void deleteInquiry(Long inquiryId) {
        inquiryRepository.deleteById(inquiryId);
    }

    public Page<InquiryDTO> getMyInquiries(int pagingIndex, int pagingSize, Principal principal)
    {
        String userEmail = principal.getName();
        Pageable pageable = PageRequest.of(pagingIndex, pagingSize);
        Page<Inquiry> inquiries = inquiryRepository.findByUserEmail(userEmail, pageable);
        List<InquiryDTO> inquiryDTOS = inquiries.stream()
                .map(InquiryDTO::toInquiryDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(inquiryDTOS, inquiries.getPageable(), inquiries.getTotalElements());
    }
}