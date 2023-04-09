package com.capstone.timepay.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Comment findByDCommentId(Long d_commentId);

    Comment findByFCommentId(Long f_commentId);
}
