package com.capstone.timepay.service.admin;

import com.capstone.timepay.controller.admin.response.inquiry.InquiryResponse;
import com.capstone.timepay.domain.inquiry.Inquiry;
import com.capstone.timepay.domain.inquiry.InquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InquiryManagerService {

    private final InquiryRepository inquiryRepository;

    public List<InquiryResponse> showAllInquiries() {
        List<Inquiry> inquiries = inquiryRepository.findAll();

        return inquiries.stream()
                        .map(inquiry -> InquiryResponse.builder()
                                .state(inquiry.getState())
                                .category(inquiry.getCategory())
                                .createdAt(inquiry.getCreatedAt())
                                .writer(inquiry.getUser().getName())
                                .title(inquiry.getTitle())
                                .build())
                        .sorted(Comparator.comparing(InquiryResponse::getCreatedAt).reversed())
                        .collect(Collectors.toList());
    }

    public List<InquiryResponse> searchInquiriesByQuery(String state, String category, String writer, String title) {

        List<Inquiry> inquiries = new ArrayList<>();

        if(state.equals("all") && category.equals("all")){
            inquiries = inquiryRepository.findAll();
        }
        else if(state.equals("all")){
            inquiries = inquiryRepository.findAllByCategory(category).orElseThrow(()->new IllegalArgumentException("존재하지 않는 문의입니다."));
        }
        else if(category.equals("all")){
            inquiries = inquiryRepository.findAllByState(state).orElseThrow(()->new IllegalArgumentException("존재하지 않는 문의입니다."));
        }
        else{
            inquiries = inquiryRepository.findAllByCategoryAndState(category, state).orElseThrow(()->new IllegalArgumentException("존재하지 않는 문의입니다."));
        }

        return inquiries.stream()
                        .map(inquiry -> InquiryResponse.builder()
                                .title(inquiry.getTitle())
                                .category(inquiry.getCategory())
                                .writer(inquiry.getUser().getName())
                                .state(inquiry.getState())
                                .createdAt(inquiry.getCreatedAt())
                                .build())
                        .filter(inquiryResponse -> inquiryResponse.getWriter().equals(writer) && !writer.equals("all"))
                        .filter(inquiryResponse -> inquiryResponse.getTitle().contains(title) && !title.equals("all"))
                        .sorted(Comparator.comparing(InquiryResponse::getCreatedAt).reversed())
                        .collect(Collectors.toList());
    }



















}
