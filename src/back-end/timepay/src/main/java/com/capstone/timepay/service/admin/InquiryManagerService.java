package com.capstone.timepay.service.admin;

import ch.qos.logback.core.pattern.Converter;
import com.capstone.timepay.controller.admin.response.inquiry.AdminAnswerResponse;
import com.capstone.timepay.controller.admin.response.inquiry.InquiryDetailResponse;
import com.capstone.timepay.controller.admin.response.inquiry.InquiryResponse;
import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.domain.admin.AdminRepository;
import com.capstone.timepay.domain.inquiry.Inquiry;
import com.capstone.timepay.domain.inquiry.InquiryRepository;
import com.capstone.timepay.domain.inquiryAnswer.InquiryAnswer;
import com.capstone.timepay.domain.inquiryAnswer.InquiryAnswerRepository;
import com.capstone.timepay.domain.user.User;
import com.capstone.timepay.domain.user.UserRepository;
import com.capstone.timepay.service.admin.dto.InquiryAnswerDto;
import org.springframework.data.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;


@Slf4j
@Service
@RequiredArgsConstructor
public class InquiryManagerService {

    private final InquiryRepository inquiryRepository;

    private final InquiryAnswerRepository inquiryAnswerRepository;
    private final UserRepository userRepository;

    public Page<InquiryResponse> convertResponsePages(Page<Inquiry> pages){
        Page<InquiryResponse> pageResponses = pages.map(new Function<Inquiry, InquiryResponse>() {
            @Override
            public InquiryResponse apply(Inquiry inquiry) {
                return InquiryResponse.builder()
                        .inquiryId(inquiry.getInquiryId())
                        .state(inquiry.getState())
                        .category(inquiry.getCategory())
                        .createdAt(inquiry.getCreatedAt())
                        .writer(inquiry.getUser().getName())
                        .title(inquiry.getTitle())
                        .build();
            }
        });

        return pageResponses;
    }

    public Page<InquiryResponse> showAllInquiries(int pageIndex, int pageSize) {

        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createdAt").descending());
        Page<Inquiry> pages = inquiryRepository.findAll(pageable);

        return convertResponsePages(pages);
    }

    public void saveInquiryAnswer(InquiryAnswerDto answer, Admin admin) {

        Inquiry inquiry = inquiryRepository.findById(answer.getInquiryId()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 문의입니다."));
        InquiryAnswer inquiryAnswer = InquiryAnswer.getNewInstance(answer, inquiry, admin);

        inquiryAnswerRepository.save(inquiryAnswer);
    }

    public InquiryDetailResponse showInquiryDetail(Long inquiryId) {

        Inquiry inquiry = inquiryRepository.findById(inquiryId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 문의입니다."));
        List<InquiryAnswer> inquiryAnswers = inquiryAnswerRepository.findAllByInquiry(inquiry).orElseThrow(()->new IllegalArgumentException("존재하지 않는 문의 답변입니다."));

        InquiryResponse inquiryResponse =
                InquiryResponse.builder()
                                .inquiryId(inquiry.getInquiryId())
                                .state(inquiry.getState())
                                .category(inquiry.getCategory())
                                .createdAt(inquiry.getCreatedAt())
                                .writer(inquiry.getUser().getName())
                                .title(inquiry.getTitle())
                                .build();
        List<AdminAnswerResponse> adminAnswerResponses =
                inquiryAnswers.stream()
                                .map(inquiryAnswer -> AdminAnswerResponse.builder()
                                        .content(inquiryAnswer.getContent())
                                        .adminName(inquiryAnswer.getAdmin().getAdminName())
                                        .createdAt(inquiryAnswer.getCreatedAt())
                                        .build())
                                .collect(Collectors.toList());

        return new InquiryDetailResponse(inquiryResponse,adminAnswerResponses);

    }

    public Page<InquiryResponse> searchInquiriesByQuery(String state, String category, String writer, String title, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        if(!ObjectUtils.isEmpty(writer) && ObjectUtils.isEmpty(title)){
            User user = userRepository.findByName(writer).orElseThrow(IllegalArgumentException::new);
            Page<Inquiry> pages = inquiryRepository.findAllByUser(user, pageable);

            List<InquiryResponse> responses = convertResponsePages(pages)
                    .stream()
                    .filter(inquiryResponse -> inquiryResponse.getState().equals(state))
                    .filter(inquiryResponse -> inquiryResponse.getCategory().equals(category))
                    .collect(Collectors.toList());

            return new PageImpl<>(responses);
        }
        else if(ObjectUtils.isEmpty(writer) && !ObjectUtils.isEmpty(title)){
            Page<Inquiry> pages = inquiryRepository.findByTitleContains(title, pageable);

            List<InquiryResponse> responses = convertResponsePages(pages)
                    .stream()
                    .filter(inquiryResponse -> inquiryResponse.getState().equals(state))
                    .filter(inquiryResponse -> inquiryResponse.getCategory().equals(category))
                    .collect(Collectors.toList());

            return new PageImpl<>(responses);
        }
        else if(!ObjectUtils.isEmpty(writer) && !ObjectUtils.isEmpty(title)){

            Page<Inquiry> pages = inquiryRepository.findByTitleContains(title, pageable);

            List<InquiryResponse> responses = convertResponsePages(pages)
                    .stream()
                    .filter(inquiryResponse -> inquiryResponse.getState().equals(state))
                    .filter(inquiryResponse -> inquiryResponse.getCategory().equals(category))
                    .filter(inquiryResponse -> inquiryResponse.getWriter().equals(writer))
                    .collect(Collectors.toList());

            return new PageImpl<>(responses);
        }
        else{
            Page<Inquiry> pages = inquiryRepository.findAll(pageable);

            List<InquiryResponse> responses = convertResponsePages(pages)
                    .stream()
                    .filter(inquiryResponse -> inquiryResponse.getState().equals(state))
                    .filter(inquiryResponse -> inquiryResponse.getCategory().equals(category))
                    .collect(Collectors.toList());

            return new PageImpl<>(responses);
        }

    }
}
