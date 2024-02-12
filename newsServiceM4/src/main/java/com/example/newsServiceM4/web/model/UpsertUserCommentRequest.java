package com.example.newsServiceM4.web.model;

import lombok.Data;

@Data
public class UpsertUserCommentRequest {

    private Long userId;

    private Long newsId;

    private String comment;


}
