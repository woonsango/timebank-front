package com.capstone.timepay.controller.board;

import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.freeRegister.FreeRegister;
import com.capstone.timepay.service.board.service.DealRegisterService;
import com.capstone.timepay.service.board.service.FreeRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegisterController {

    private final FreeRegisterService freeRegisterService;

    private final DealRegisterService dealRegisterService;

    @GetMapping("/free")
    public ResponseEntity<List<FreeRegister>> getAllFreeRegisters() {
        List<FreeRegister> freeRegisters = freeRegisterService.getAllFreeRegisters();
        if (freeRegisters.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(freeRegisters);
        }
    }

    @GetMapping("/deal")
    public ResponseEntity<List<DealRegister>> getAllDealRegisters() {
        List<DealRegister> dealRegisters = dealRegisterService.getAllDealRegisters();
        if (dealRegisters.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(dealRegisters);
        }
    }
}
