package com.capstone.timepay.domain.dealBoardComment;

import com.capstone.timepay.domain.board.BoardStatus;
import com.capstone.timepay.domain.dealBoard.DealBoard;
import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.user.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.List;

public class DealBoardCommentSearch {
    public static Specification<DealBoardComment> withApplied(Boolean isApplied) {
        if (isApplied == null) {
            return null;
        }
        return (root, query, builder) -> builder.equal(root.get("isApplied"), isApplied);
    }


    public static Specification<DealBoardComment> withAdopted(Boolean isAdopted) {
        if (isAdopted == null) {
            return null;
        }
        return (root, query, builder) -> builder.equal(root.get("isAdopted"), isAdopted);
    }

    public static Specification<DealBoardComment> withUser(User user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), user);
    }

}
