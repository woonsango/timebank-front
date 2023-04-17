package com.capstone.timepay.domain.board;

import org.springframework.data.jpa.domain.Specification;

/**
 * GET /api/boards?sort={sort}&category={category}
 */

public class BoardSearch {

    public static Specification<Board> latestFirst() {
        return (root, query, builder) -> {
            query.orderBy(builder.desc(root.get("createdDate")));
            return builder.conjunction();
        };
    }

    public static Specification<Board> oldestFirst() {
        return (root, query, builder) -> {
            query.orderBy(builder.asc(root.get("createdDate")));
            return builder.conjunction();
        };
    }

    public static Specification<Board> freeBoard() {
        return (root, query, builder) -> builder.isNotNull(root.get("freeBoard"));
    }

    public static Specification<Board> dealBoard() {
        return (root, query, builder) -> builder.isNotNull(root.get("dealBoard"));
    }

    public static Specification<Board> createdBy(String user) {
        return (root, query, builder) -> builder.or(
                builder.like(root.get("freeBoard").get("createdBy"), "%" + user + "%"),
                builder.like(root.get("dealBoard").get("createdBy"), "%" + user + "%")
        );
    }

    public static Specification<Board> category(String category) {
        return (root, query, builder) -> builder.or(
                builder.like(root.get("freeBoard").get("category"), "%" + category + "%"),
                builder.like(root.get("dealBoard").get("category"), "%" + category + "%")
        );
    }
}
