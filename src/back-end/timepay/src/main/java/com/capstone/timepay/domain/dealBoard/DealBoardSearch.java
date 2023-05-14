package com.capstone.timepay.domain.dealBoard;

import com.capstone.timepay.domain.dealRegister.DealRegister;
import com.capstone.timepay.domain.user.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.time.LocalDateTime;

public class DealBoardSearch {

    public static Specification<DealBoard> withTitle(String title) {
        return (root, query, builder) -> {
            if (StringUtils.isEmpty(title)) {
                return null;
            }
            return builder.equal(root.get("title"), title);
        };
    }

    public static Specification<DealBoard> withName(String name) {
        return (root, query, builder) -> {
            if (StringUtils.isEmpty(name)) {
                return null;
            }
            // DealBoard와 DealRegister를 참조하는 join
            Join<DealBoard, DealRegister> dealRegisterJoin = root.join("dealRegisters", JoinType.INNER);

            // DealRegister와 User를 참조하는 join
            Join<DealRegister, User> userJoin = dealRegisterJoin.join("user", JoinType.INNER);

            return builder.equal(userJoin.get("name"), name);
        };
    }

    public static Specification<DealBoard> withType(String type) {
        return (root, query, builder) -> {
            if (StringUtils.isEmpty(type)) {
                return null;
            }
            return builder.equal(root.get("type"), type);
        };
    }

    public static Specification<DealBoard> withCategory(String category) {
        return (root, query, builder) -> {
            if (StringUtils.isEmpty(category)) {
                return null;
            }
            return builder.equal(root.get("category"), category);
        };
    }

    public static Specification<DealBoard> withStartTime(LocalDateTime startTime) {
        return (root, query, builder) -> {
            if (startTime == null) {
                return null;
            }
            return builder.greaterThanOrEqualTo(root.get("startTime"), startTime);
        };
    }

    public static Specification<DealBoard> withEndTime(LocalDateTime endTime) {
        return (root, query, builder) -> {
            if (endTime == null) {
                return null;
            }
            return builder.lessThanOrEqualTo(root.get("endTime"), endTime);
        };
    }

    public static Specification<DealBoard> withVolunteer(boolean isVolunteer) {
        return (root, query, builder) -> builder.equal(root.get("isVolunteer"), isVolunteer);
    }
}