package com.capstone.timepay.domain.certification;

import com.capstone.timepay.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification,Long> {
    List<Certification> findAllByDealBoardId(Long boardId);

    Certification findByUserAndDealBoardId(User user, Long boardId);

    Certification findAllByUser(User user);

    Page<Certification> findAllByUserAndIsPublishedTrue(User user, Pageable pageable);
    List<Certification> findAllByUserAndIsPublishedTrue(User user);
}
