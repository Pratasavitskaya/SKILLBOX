package com.example.newsServiceM4.service;

import com.example.newsServiceM4.model.NewsCategory;

import java.util.List;

public interface NewsCategoryService {

    List<NewsCategory> findAll();

    NewsCategory findById(Long id);

    NewsCategory save(NewsCategory newsCategory);

    NewsCategory update(NewsCategory newsCategory);

    void deleteById(Long id);
}
