package com.capstone.timepay.domain.dealBoardComment;

import com.capstone.timepay.domain.board.BoardStatus;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.user.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.List;

public class DealBoardCommentSearch {
    public static Specification<DealBoardComment> withApplied(Boolean applied) {
        return (root, query, builder) -> builder.equal(root.get("isApplied"), applied);
    }

    public static Specification<DealBoardComment> withAdopted(Boolean adopted) {
        return (root, query, builder) -> builder.equal(root.get("isAdopted"), adopted);
    }

    public static Specification<DealBoardComment> withUser(User user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), user);
    }

}
