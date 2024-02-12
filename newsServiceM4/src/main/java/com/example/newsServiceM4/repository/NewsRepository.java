package com.example.newsServiceM4.repository;

import com.example.newsServiceM4.model.News;

import java.util.List;
import java.util.Optional;

public interface NewsRepository {
    List<News> findAll();

    Optional<News> findById(Long id);

    News save(News news);

    News update(News news);

    void deleteById(Long id);

    void deleteByIdIn(List<Long> ids);
}
