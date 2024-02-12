package com.example.newsServiceM4.service;

import com.example.newsServiceM4.model.UserComment;

import java.util.List;

public interface CommentService {

    List<UserComment> findAll();

    UserComment findById(Long id);

    UserComment save(UserComment userComment);

    UserComment update(UserComment userComment);

    void deleteById(Long id);

    void deleteByIdIn(List<Long> ids);
}
