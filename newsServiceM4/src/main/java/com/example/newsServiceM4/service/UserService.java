package com.example.newsServiceM4.service;

import com.example.newsServiceM4.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User save(User user);

    User update(User user);

    void deleteById(Long id);

    void deleteByIn(List<Long> ids);
}
