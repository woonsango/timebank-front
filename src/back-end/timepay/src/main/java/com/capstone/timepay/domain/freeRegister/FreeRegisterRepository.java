package com.capstone.timepay.domain.freeRegister;

import com.capstone.timepay.domain.freeBoard.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FreeRegisterRepository extends JpaRepository<FreeRegister,Long> {
    Optional<FreeRegister> findByFreeBoard(FreeBoard freeBoard);
}
