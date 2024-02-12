package com.example.newsServiceM4.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class NewsFilter {

    private Integer pageSize;

    private Integer pageNumber;

    private String category;

    private String author;

}
