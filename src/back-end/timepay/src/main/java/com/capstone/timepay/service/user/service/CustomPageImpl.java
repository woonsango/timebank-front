package com.capstone.timepay.service.user.service;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CustomPageImpl<T> extends PageImpl<T> {
    public CustomPageImpl(List<T> content, Pageable pageable) {
        super(content, pageable, content == null ? 0 : content.size());
    }
}