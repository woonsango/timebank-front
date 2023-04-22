package com.capstone.timepay.service.board.service;

import com.capstone.timepay.domain.freeRegister.FreeRegister;
import com.capstone.timepay.domain.freeRegister.FreeRegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FreeRegisterService {

    private FreeRegisterRepository freeRegisterRepository;

    public FreeRegister getId(Long id)
    {
        return freeRegisterRepository.findById(id).orElse(null);
    }

    // 해당 레지스터에서 유저 Email을 가져오는 메서드
    public String getEmail(Long boardId)
    {
        FreeRegister freeRegister = freeRegisterRepository.findById(boardId).orElse(null);
        return freeRegister.getUser().getEmail();
    }

    public FreeRegister getFreeRegisterById(Long id) {
        return freeRegisterRepository.findById(id).orElse(null);
    }

    public List<FreeRegister> getAllFreeRegisters() {
        return freeRegisterRepository.findAll();
    }
}
