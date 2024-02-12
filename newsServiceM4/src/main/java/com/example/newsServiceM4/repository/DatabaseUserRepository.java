package com.example.newsServiceM4.repository;

import com.example.newsServiceM4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseUserRepository extends JpaRepository<User, Long> {
}
