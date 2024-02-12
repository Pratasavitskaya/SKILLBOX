package com.example.newsServiceM4.mapper.v2;


import com.example.newsServiceM4.model.NewsCategory;
import com.example.newsServiceM4.web.model.NewsCategoryListResponse;
import com.example.newsServiceM4.web.model.NewsCategoryResponse;
import com.example.newsServiceM4.web.model.UpsertNewsCategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {NewsMapperV2.class})
public interface NewsCategoryMapper {


    NewsCategory requestToNewsCategory(UpsertNewsCategoryRequest request);

    @Mapping(source = "newsCategoryId", target = "id")
    NewsCategory requestToNewsCategory(Long newsCategoryId, UpsertNewsCategoryRequest request);

    NewsCategoryResponse newsCategoryToResponse(NewsCategory newsCategory);

    default NewsCategoryListResponse newsCategoryListToNewsCategoryResponseList(List<NewsCategory> newsCategories) {
        NewsCategoryListResponse response = new NewsCategoryListResponse();

        response.setNewsCategoryList(newsCategories.stream()
                .map(this::newsCategoryToResponse).collect(Collectors.toList()));

        return response;
    }

}
