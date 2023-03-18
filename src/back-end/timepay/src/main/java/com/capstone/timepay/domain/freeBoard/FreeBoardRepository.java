package com.capstone.timepay.domain.freeBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FreeBoardRepository extends JpaRepository<FreeBoard,Long> {
    @Modifying
    @Query(value = "update FreeBoard fb set fb.freeBoardHits=fb.freeBoardHits+1 where fb.id=:id")
    void updateHits(@Param("id") Long id);
}
