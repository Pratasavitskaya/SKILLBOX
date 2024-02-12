package com.example.newsServiceM4.service.impl;

import com.example.newsServiceM4.aop.Loggable;
import com.example.newsServiceM4.exception.EntityNotFoundException;
import com.example.newsServiceM4.model.News;
import com.example.newsServiceM4.repository.DatabaseNewsRepository;
import com.example.newsServiceM4.repository.NewsSpecification;
import com.example.newsServiceM4.service.NewsService;
import com.example.newsServiceM4.utils.BeanUtils;
import com.example.newsServiceM4.web.model.NewsFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DatabaseNewsService implements NewsService {

    private final DatabaseNewsRepository newsRepository;

    @Override
    public List<News> filterBy(NewsFilter filter) {

        return newsRepository.findAll(NewsSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();

    }


    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format("Новость с ID: {0} не найдена!", id)
                ));
    }

    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }

    @Override
    @Loggable
    public News update(News news) {

        News existedNews = findById(news.getId());

        BeanUtils.copyNonNullProperties(news, existedNews);
        return newsRepository.save(existedNews);
    }

    @Override
    @Loggable
    @Transactional
    public void deleteById(Long id) {

        newsRepository.deleteById(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        newsRepository.deleteAllById(ids);

    }

    public boolean authorVerification(Long id, String author){

       News news=findById(id);

       return news.getAuthor().equals(author);

    }
}
