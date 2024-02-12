package com.example.newsServiceM4.service.impl;

import com.example.newsServiceM4.exception.EntityNotFoundException;
import com.example.newsServiceM4.model.NewsCategory;
import com.example.newsServiceM4.repository.DatabaseNewsCategoryRepository;
import com.example.newsServiceM4.service.NewsCategoryService;
import com.example.newsServiceM4.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseNewsCategoryService implements NewsCategoryService {

    private final DatabaseNewsCategoryRepository newsCategoryRepository;

    @Override
    public List<NewsCategory> findAll() {
        return newsCategoryRepository.findAll();
    }

    @Override
    public NewsCategory findById(Long id) {
        return newsCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException
                        (MessageFormat.format("Категория с ID:{0} не найдена!", id)));
    }

    @Override
    public NewsCategory save(NewsCategory newsCategory) {

        return newsCategoryRepository.save(newsCategory);
    }

    @Override
    public NewsCategory update(NewsCategory newsCategory) {
        NewsCategory existedNewsCategory = findById(newsCategory.getId());
        BeanUtils.copyNonNullProperties(newsCategory, existedNewsCategory);
        return newsCategoryRepository.save(newsCategory);
    }

    @Override
    public void deleteById(Long id) {
        newsCategoryRepository.deleteById(id);
    }
}
