package com.capstone.timepay.domain.dealBoard;

import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DealBoardRepository extends JpaRepository<DealBoard,Long>, JpaSpecificationExecutor<DealBoard> {

    List<DealBoard> findByDealRegistersIn(List<DealRegister> dealRegisters);
    Page<DealBoard> findByIsHiddenFalse(Pageable pageable);
    Page<DealBoard> findByType(Pageable pageable, String Type);

    long countByIsHiddenFalse();
}
