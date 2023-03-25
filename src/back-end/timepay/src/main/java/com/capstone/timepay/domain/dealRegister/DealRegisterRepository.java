package com.capstone.timepay.domain.dealRegister;

import com.capstone.timepay.domain.dealBoard.DealBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DealRegisterRepository extends JpaRepository<DealRegister,Long> {
    Optional<DealRegister> findByDealBoard(DealBoard dealBoard);
}
