package com.capstone.timepay.domain.board;

import org.springframework.data.jpa.domain.Specification;

/**
 * GET /api/boards?sort={sort}&category={category}
 */

public class BoardSearch {
    // 카테고리별
    public static Specification<Board> withCategory(String category) {
        return (root, query, builder) -> builder.equal(root.get("category"), category);
    }

    // 최신순
    public static Specification<Board> latest() {
        return (root, query, builder) -> {
            query.orderBy(builder.desc(root.get("createdAt")));
            return builder.conjunction();
        };
    }

    // 과거순
    public static Specification<Board> oldest() {
        return (root, query, builder) -> {
            query.orderBy(builder.asc(root.get("createdAt")));
            return builder.conjunction();
        };
    }
}
