package com.capstone.timepay.domain.freeRegister;

import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface FreeRegisterRepository extends JpaRepository<FreeRegister,Long> {
    Optional<FreeRegister> findByFreeBoard(FreeBoard freeBoard);

    List<FreeRegister> findAllByUser(User user);
    Page<FreeRegister> findAllByUser(User user, Pageable pageable);

    List<FreeRegister> findTop10ByUser(User user, Sort sort);
}
