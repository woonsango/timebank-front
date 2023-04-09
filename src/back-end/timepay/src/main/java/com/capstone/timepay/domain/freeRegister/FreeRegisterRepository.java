package com.capstone.timepay.domain.freeRegister;

import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FreeRegisterRepository extends JpaRepository<FreeRegister,Long> {
    Optional<FreeRegister> findByFreeBoard(FreeBoard freeBoard);

    List<FreeRegister> findAllByUser(User user);
}
