package com.example.newsServiceM4.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCommentResponse {

    private Long id;

    private String comment;

    private UserResponse user;

    private NewsResponseForList news;
}
