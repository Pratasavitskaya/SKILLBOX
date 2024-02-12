package com.example.newsServiceM4.repository;

import com.example.newsServiceM4.model.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseNewsCategoryRepository  extends JpaRepository<NewsCategory, Long> {
}
