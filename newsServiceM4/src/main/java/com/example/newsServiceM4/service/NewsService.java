package com.example.newsServiceM4.service;

import com.example.newsServiceM4.model.News;
import com.example.newsServiceM4.web.model.NewsFilter;

import java.util.List;

public interface NewsService {

    List<News> filterBy(NewsFilter filter);

    List<News> findAll();

    News findById(Long id);

    News save(News news);

    News update(News news);

    void deleteById(Long id);

    void deleteByIdIn(List<Long> ids);

    boolean authorVerification(Long id, String author);
}
