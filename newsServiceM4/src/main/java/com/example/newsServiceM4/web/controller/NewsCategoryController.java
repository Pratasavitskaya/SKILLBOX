package com.example.newsServiceM4.web.controller;

import com.example.newsServiceM4.mapper.v2.NewsCategoryMapper;
import com.example.newsServiceM4.model.NewsCategory;
import com.example.newsServiceM4.service.NewsCategoryService;
import com.example.newsServiceM4.web.model.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/newsCategory")
public class NewsCategoryController {

    private final NewsCategoryService categoryService;

    private final NewsCategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<NewsCategoryListResponse> findAll() {
        return ResponseEntity.ok(
                categoryMapper.newsCategoryListToNewsCategoryResponseList(categoryService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsCategoryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryMapper.newsCategoryToResponse(categoryService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<NewsCategoryResponse> create(@RequestBody @Valid UpsertNewsCategoryRequest request) {
        NewsCategory newNewsCategory = categoryService.save(categoryMapper.requestToNewsCategory(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper.newsCategoryToResponse(newNewsCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsCategoryResponse> update(@PathVariable("id") Long userId,
                                                       @RequestBody @Valid UpsertNewsCategoryRequest request) {
        NewsCategory updatedCategory = categoryService.update(categoryMapper.requestToNewsCategory(userId, request));

        return ResponseEntity.ok(categoryMapper.newsCategoryToResponse(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
