package com.example.newsServiceM4.mapper.v2;

import com.example.newsServiceM4.model.News;
import com.example.newsServiceM4.web.model.NewsListResponse;
import com.example.newsServiceM4.web.model.NewsResponse;
import com.example.newsServiceM4.web.model.NewsResponseForList;
import com.example.newsServiceM4.web.model.UpsertNewsRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {NewsCategoryMapper.class})
public interface NewsMapperV2 {


    News requestToNews(UpsertNewsRequest request);

    @Mapping(source = "newsId", target = "id")
    News requestToNews(Long newsId, UpsertNewsRequest request);

    NewsResponse newsToResponse(News news);

    List<NewsResponseForList> newsListToResponseList(List<News> newsList);


    default NewsResponseForList newsToResponseForList(News news) {

        if (news == null) {
            return null;
        }

        NewsResponseForList newsResponseForList = new NewsResponseForList();

        NewsCategoryMapper categoryMapper = new NewsCategoryMapperImpl();
        newsResponseForList.setAuthor(news.getAuthor());
        newsResponseForList.setId(news.getId());
        newsResponseForList.setCategory(categoryMapper.newsCategoryToResponse(news.getCategory()));
        newsResponseForList.setNewsText(news.getNewsText());
        newsResponseForList.setTitle(news.getTitle());
        newsResponseForList.setNumberOfComments((long) news.getUserComments().size());

        return newsResponseForList;
    }

    default NewsListResponse newsListToNewsListResponse(List<News> newsList) {
        NewsListResponse response = new NewsListResponse();
        response.setNewsList(newsListToResponseList(newsList));
        return response;
    }
}
