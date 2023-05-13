package com.capstone.timepay.service.board.service;

import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.dealRegister.DealRegisterRepository;
import com.capstone.timepay.domain.freeRegister.FreeRegister;
import com.capstone.timepay.domain.freeRegister.FreeRegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealRegisterService {

    private final DealRegisterRepository dealRegisterRepository;

    public DealRegister getId(Long id)
    {
        return dealRegisterRepository.findById(id).orElse(null);
    }

    // 해당 레지스터에서 유저 Email을 가져오는 메서드
    public String getEmail(Long boardId)
    {
        System.out.println(boardId);
        DealRegister dealRegister = dealRegisterRepository.findById(boardId).orElse(null);
        return dealRegister.getUser().getEmail();
    }

    public List<DealRegister> getAllDealRegisters() {
        return dealRegisterRepository.findAll();
    }
}
