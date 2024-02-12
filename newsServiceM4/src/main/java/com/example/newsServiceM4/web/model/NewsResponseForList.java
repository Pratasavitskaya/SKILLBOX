package com.example.newsServiceM4.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponseForList {

    private Long id;

    private String author;

    private NewsCategoryResponse category;

    private String newsText;

    private String title;

    private Long numberOfComments;

}
