package com.example.newsServiceM4.repository;

import com.example.newsServiceM4.model.UserComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseCommentRepository extends JpaRepository<UserComment, Long> {
}
