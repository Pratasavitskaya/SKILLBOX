package com.example.newsServiceM4;

import com.example.newsServiceM4.model.News;
import com.example.newsServiceM4.model.NewsCategory;
import com.example.newsServiceM4.model.User;
import com.example.newsServiceM4.model.UserComment;
import com.example.newsServiceM4.web.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles("test")
public abstract class AbstractTestController {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected News createNews (Long id, NewsCategory category){
        return new News(
                id,
                "Title " + id,
                "NewsText " + id,
                "Author " + id,
                category,
                new ArrayList<>()
        );

      }

      protected NewsCategory createCategory(Long id){

        return new NewsCategory(id, "category "+ id, new ArrayList<>());
      }

      protected User createUser(Long id){

        return new User(id, "name "+id, new ArrayList<>());

      }

      protected UserComment createComment(Long id, User user, News news ){

        return new UserComment(id, user, "comment "+id, news);

      }

      protected NewsResponseForList createNewsResponseForList(Long id, NewsCategoryResponse categoryResponse){
        return new NewsResponseForList(
                id,
                "Author " + id,
                categoryResponse,
                "NewsText " + id,
                "Title " + id,
              0L

        );
      }
    protected NewsResponse createNewsResponse(Long id,
                                              NewsCategoryResponse categoryResponse,
                                              UserCommentListResponse comments){
        return new NewsResponse(
                id,
                "Author " + id,
                categoryResponse,
                "NewsText " + id,
                "Title " + id,
                comments

        );
    }


      protected NewsCategoryResponse createCategoryResponse(Long id){
        return new NewsCategoryResponse(id, "category " +id);

      }
      protected UserResponse createUserResponse(Long id){
        return new UserResponse(id,"user "+id);
      }

      protected UserCommentResponse createCommentResponse(Long id, UserResponse userResponse,
                                                          NewsResponseForList newsResponse){
        return new UserCommentResponse(id, "comment "+id, userResponse, newsResponse);
      }




}
