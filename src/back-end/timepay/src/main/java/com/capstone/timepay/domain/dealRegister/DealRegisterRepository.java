package com.capstone.timepay.domain.dealRegister;

import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.freeRegister.FreeRegister;
import com.capstone.timepay.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface DealRegisterRepository extends JpaRepository<DealRegister,Long> {
    Optional<DealRegister> findByDealBoard(DealBoard dealBoard);

    List<DealRegister> findAllByUser(User user);
    Page<DealRegister> findAllByUser(User user, Pageable pageable);
}
