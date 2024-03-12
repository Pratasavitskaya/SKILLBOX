package com.example.newsServiceM4.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertUserCommentRequest {

    private Long userId;

    private Long newsId;
    @NotBlank(message = "Комментарий не может быть пустым!")
    private String comment;


}
