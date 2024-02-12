package com.example.newsServiceM4.repository;

import com.example.newsServiceM4.model.News;
import com.example.newsServiceM4.web.model.NewsFilter;
import org.springframework.data.jpa.domain.Specification;

public interface NewsSpecification {
    static Specification<News> withFilter(NewsFilter newsFilter) {
        return Specification.where(byCategory(newsFilter.getCategory())
                .and(byAuthor(newsFilter.getAuthor())));
    }

    static Specification<News> byAuthor(String author) {
        return (root, query, cb) -> {
            if (author == null) {
                return null;
            }

            return cb.equal(root.get("user").get("name"), author);
        };
    }

    static Specification<News> byCategory(String categoryName) {
        return (root, query, cb) -> {
            if (categoryName == null) {
                return null;
            }

            return cb.equal(root.get("category").get("category"), categoryName);
        };
    }
}
