package com.example.newsServiceM4.mapper.v2;

import com.example.newsServiceM4.model.News;
import com.example.newsServiceM4.service.NewsCategoryService;
import com.example.newsServiceM4.web.model.NewsResponse;
import com.example.newsServiceM4.web.model.UpsertNewsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class NewsMapperDelegate implements NewsMapperV2 {

    @Autowired
    private NewsCategoryMapper categoryMapper;

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private NewsCategoryService databaseNewsCategoryService;

    @Override
    public News requestToNews(UpsertNewsRequest request) {
        News news = new News();
        news.setTitle(request.getTitle());
        news.setNewsText(request.getNewsText());
        news.setAuthor(request.getAuthor());
        news.setCategory(databaseNewsCategoryService.findById(request.getCategoryId()));

        return news;
    }

    @Override
    public NewsResponse newsToResponse(News news) {
        NewsResponse newsResponse = new NewsResponse();

        newsResponse.setAuthor(news.getAuthor());
        newsResponse.setNewsText(news.getNewsText());
        newsResponse.setTitle(news.getTitle());
        newsResponse.setCategory(categoryMapper.newsCategoryToResponse(news.getCategory()));
        newsResponse.setId(news.getId());
        newsResponse.setComments(commentsMapper.userCommentListToUserCommentResponseListForNews(news.getUserComments()));

        return newsResponse;
    }

    @Override
    public News requestToNews(Long newsId, UpsertNewsRequest request) {
        News news = requestToNews(request);
        news.setId(newsId);

        return news;
    }

}
