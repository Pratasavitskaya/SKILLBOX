package com.example.newsServiceM4.web.controller;

import com.example.newsServiceM4.exception.EntityNotFoundException;
import com.example.newsServiceM4.exception.UpdateStateException;
import com.example.newsServiceM4.mapper.v2.NewsMapperV2;
import com.example.newsServiceM4.model.News;
import com.example.newsServiceM4.service.NewsService;
import com.example.newsServiceM4.web.model.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/news")
public class NewsControllerV2 {

    private final NewsService databaseNewsService;

    private final NewsMapperV2 newsMapper;

    @GetMapping
    public ResponseEntity<NewsListResponse> findAll() {
        return ResponseEntity.ok(
                newsMapper.newsListToNewsListResponse(
                        databaseNewsService.findAll()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                newsMapper.newsToResponse(databaseNewsService.findById(id))
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<NewsListResponse> filterBy(NewsFilter categoryId) {


        return ResponseEntity.ok(
                newsMapper.newsListToNewsListResponse(databaseNewsService.filterBy(categoryId))
        );
    }

    @PostMapping
    public ResponseEntity<NewsResponse> create(@RequestBody @Valid UpsertNewsRequest request) {
        News newNews = databaseNewsService.save(newsMapper.requestToNews(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsMapper.newsToResponse(newNews));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsResponse> update(@PathVariable("id") Long newsId,
                                               @RequestBody @Valid UpsertNewsRequest request) {
        News updatedNews = databaseNewsService.update(newsMapper.requestToNews(newsId, request));

        return ResponseEntity.ok(newsMapper.newsToResponse(updatedNews));

    }

    @DeleteMapping("/{id},{author}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @PathVariable String author) {
   if (databaseNewsService.authorVerification(id, author)){
        databaseNewsService.deleteById(id);

         return ResponseEntity.noContent().build();
   }
             else
       throw new EntityNotFoundException("Удалять новость может только тот, кто ее создал!");
       // return ResponseEntity.badRequest().build();
    }


}
