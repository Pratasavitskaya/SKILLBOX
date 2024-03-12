package com.example.newsServiceM4.web.controller;

import com.example.newsServiceM4.AbstractTestController;
import com.example.newsServiceM4.StringTestUtils;
import com.example.newsServiceM4.exception.EntityNotFoundException;
import com.example.newsServiceM4.mapper.v2.CommentsMapper;
import com.example.newsServiceM4.mapper.v2.NewsCategoryMapper;
import com.example.newsServiceM4.mapper.v2.NewsMapperV2;
import com.example.newsServiceM4.mapper.v2.UserMapperV2;
import com.example.newsServiceM4.model.News;
import com.example.newsServiceM4.model.NewsCategory;
import com.example.newsServiceM4.model.User;
import com.example.newsServiceM4.model.UserComment;
import com.example.newsServiceM4.service.CommentService;
import com.example.newsServiceM4.service.NewsCategoryService;
import com.example.newsServiceM4.service.NewsService;
import com.example.newsServiceM4.service.UserService;
import com.example.newsServiceM4.web.model.*;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NewsControllerTest extends AbstractTestController {

    @MockBean
    private NewsService newsService;

    @MockBean
    private NewsMapperV2 newsMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapperV2 userMapper;

    @MockBean
    private NewsCategoryService categoryService;

    @MockBean
    private NewsCategoryMapper categoryMapper;

    @MockBean
    private CommentService commentService;

    @MockBean
    private CommentsMapper commentsMapper;

    @Test
    public void whenFindAllNews_thenReturnAllNews() throws Exception {

        List<News> listNews = new ArrayList<>();
        NewsCategory category1 = createCategory(1L);
        listNews.add(createNews(1L, category1));
        NewsCategory category2 = createCategory(2L);
        listNews.add(createNews(2L, category2));

        List<NewsResponseForList> newsResponses = new ArrayList<>();
        NewsCategoryResponse categoryResponse1 = createCategoryResponse(1L);
        newsResponses.add(createNewsResponseForList(1L, categoryResponse1));
        NewsCategoryResponse categoryResponse2 = createCategoryResponse(2L);
        newsResponses.add(createNewsResponseForList(2L, categoryResponse2));

        NewsListResponse newsListResponse = new NewsListResponse(newsResponses);

        Mockito.when(newsService.findAll()).thenReturn(listNews);
        Mockito.when(newsMapper.newsListToNewsListResponse(listNews)).thenReturn(newsListResponse);

        String actualResponse = mockMvc.perform(get("/api/v2/news"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/find_all_news_response.json");

        Mockito.verify(newsService, Mockito.times(1)).findAll();
        Mockito.verify(newsMapper, Mockito.times(1)).newsListToNewsListResponse(listNews);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenGetNewsById_thenReturnNewsById() throws Exception {
        News news = createNews(1L, createCategory(1L));
        NewsResponse newsResponse = createNewsResponse(1L, createCategoryResponse(1L), new UserCommentListResponse());


        Mockito.when(newsService.findById(1L)).thenReturn(news);
        Mockito.when(newsMapper.newsToResponse(news)).thenReturn(newsResponse);

        String actualResponse = mockMvc.perform(get("/api/v2/news/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/find_news_by_id_response.json");

        Mockito.verify(newsService, Mockito.times(1)).findById(1L);
        Mockito.verify(newsMapper, Mockito.times(1)).newsToResponse(news);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateNews_thenReturnNewNews() throws Exception {
        News news = new News();

        news.setNewsText("NewsText 1");
        news.setTitle("Title 1");
        news.setAuthor("Author 1");
        news.setCategory(createCategory(1L));

        News createdNews = createNews(1L, createCategory(1L));
        NewsResponse newsResponse = createNewsResponse(1L, createCategoryResponse(1L), new UserCommentListResponse());

        UpsertNewsRequest request = new UpsertNewsRequest("Author 1", 1L, "Title 1", "NewsText 1");

        Mockito.when(newsService.save(news)).thenReturn(createdNews);
        Mockito.when(newsMapper.requestToNews(request)).thenReturn(news);
        Mockito.when(newsMapper.newsToResponse(createdNews)).thenReturn(newsResponse);

        String actualResponse = mockMvc.perform(post("/api/v2/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/create_news_response.json");

        Mockito.verify(newsService, Mockito.times(1)).save(news);
        Mockito.verify(newsMapper, Mockito.times(1)).requestToNews(request);
        Mockito.verify(newsMapper, Mockito.times(1)).newsToResponse(createdNews);


        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);


    }

    @Test
    public void whenUpdateNews_thenReturnUpdatedNews() throws Exception {
        UpsertNewsRequest request = new UpsertNewsRequest("Author 1", 1L, "Title 1", "NewsText new");
        News updatedNews = createNews(1L, createCategory(1L));
        NewsResponse newsResponse = new NewsResponse(1L, "Author 1",
                new NewsCategoryResponse(1L, "category 1"),
                "NewsText new", "Title 1", new UserCommentListResponse());

        Mockito.when(newsService.update(updatedNews)).thenReturn(updatedNews);
        Mockito.when(newsMapper.requestToNews(1L, request)).thenReturn(updatedNews);
        Mockito.when(newsMapper.newsToResponse(updatedNews)).thenReturn(newsResponse);

        String actualResponse = mockMvc.perform(put("/api/v2/news/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/update_news_response.json");

        Mockito.verify(newsService, Mockito.times(1)).update(updatedNews);
        Mockito.verify(newsMapper, Mockito.times(1)).requestToNews(1L, request);
        Mockito.verify(newsMapper, Mockito.times(1)).newsToResponse(updatedNews);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenFindByIdNotExistedNews_thenReturnError() throws Exception {
        Mockito.when(newsService.findById(5L)).thenThrow(new EntityNotFoundException("Новость с ID 5 не найдена!"));

        var response = mockMvc.perform(get("/api/v2/news/5"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();

        response.setCharacterEncoding("UTF-8");

        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/news_by_id_not_found_response.json");

        Mockito.verify(newsService, Mockito.times(1)).findById(5L);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenCreateNewsWithEmptyCategory_thenReturnError() throws Exception {
        var response = mockMvc.perform(post("/api/v2/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpsertNewsRequest())))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        response.setCharacterEncoding("UTF-8");
        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/empty_news_author_response.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenFindAllUser_thenReturnAllUser() throws Exception {

        List<User> listUsers = new ArrayList<>();
        listUsers.add(createUser(1L));
        listUsers.add(createUser(2L));

        List<UserResponse> usersResponse = new ArrayList<>();
        usersResponse.add(createUserResponse(1L));
        usersResponse.add(createUserResponse(2L));

        UserListResponse userListResponse = new UserListResponse(usersResponse);

        Mockito.when(userService.findAll()).thenReturn(listUsers);
        Mockito.when(userMapper.userListToUserResponseList(listUsers)).thenReturn(userListResponse);

        String actualResponse = mockMvc.perform(get("/api/v2/user"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/find_all_users_response.json");

        Mockito.verify(userService, Mockito.times(1)).findAll();
        Mockito.verify(userMapper, Mockito.times(1)).userListToUserResponseList(listUsers);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);


    }

    @Test
    public void whenGetUserById_thenReturnUserById() throws Exception {

        User user = createUser(1L);
        UserResponse userResponse = createUserResponse(1L);

        Mockito.when(userService.findById(1L)).thenReturn(user);
        Mockito.when(userMapper.userToResponse(user)).thenReturn(userResponse);

        String actualResponse = mockMvc.perform(get("/api/v2/user/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/find_user_by_id_response.json");

        Mockito.verify(userService, Mockito.times(1)).findById(1L);
        Mockito.verify(userMapper, Mockito.times(1)).userToResponse(user);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenCreateUser_thenReturnNewUser() throws Exception {

        User user = new User();
        user.setName("user 1");

        User createdUser = createUser(1L);
        UserResponse userResponse = createUserResponse(1L);

        UpsertUserRequest request = new UpsertUserRequest("user 1");

        Mockito.when(userService.save(user)).thenReturn(createdUser);
        Mockito.when(userMapper.requestToUser(request)).thenReturn(user);
        Mockito.when(userMapper.userToResponse(createdUser)).thenReturn(userResponse);

        String actualResponse = mockMvc.perform(post("/api/v2/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/create_user_response.json");

        Mockito.verify(userService, Mockito.times(1)).save(user);
        Mockito.verify(userMapper, Mockito.times(1)).requestToUser(request);
        Mockito.verify(userMapper, Mockito.times(1)).userToResponse(createdUser);


        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenUpdateUser_thenReturnUpdatedUser() throws Exception {
        UpsertUserRequest request = new UpsertUserRequest("user 2");
        User updatedUser = new User(1L, "user 2", new ArrayList<>());
        UserResponse userResponse = new UserResponse(1L, "user 2");

        Mockito.when(userService.update(updatedUser)).thenReturn(updatedUser);
        Mockito.when(userMapper.requestToUser(1L, request)).thenReturn(updatedUser);
        Mockito.when(userMapper.userToResponse(updatedUser)).thenReturn(userResponse);

        String actualResponse = mockMvc.perform(put("/api/v2/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/update_user_response.json");

        Mockito.verify(userService, Mockito.times(1)).update(updatedUser);
        Mockito.verify(userMapper, Mockito.times(1)).requestToUser(1L, request);
        Mockito.verify(userMapper, Mockito.times(1)).userToResponse(updatedUser);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenFindByIdNotExistedUser_thenReturnError() throws Exception {
        Mockito.when(userService.findById(100L)).thenThrow(new EntityNotFoundException("Пользователь с ID 100 не найден!"));

        var response = mockMvc.perform(get("/api/v2/user/100"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();

        response.setCharacterEncoding("UTF-8");

        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/user_by_id_not_found_response.json");

        Mockito.verify(userService, Mockito.times(1)).findById(100L);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateUserWithEmptyName_thenReturnError() throws Exception {
        var response = mockMvc.perform(post("/api/v2/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpsertUserRequest())))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        response.setCharacterEncoding("UTF-8");
        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/empty_user_name_response.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenFindAllCategory_thenReturnAllCategory() throws Exception {
        List<NewsCategory> categories = new ArrayList<>();
        NewsCategory category1 = createCategory(1L);
        categories.add(category1);
        NewsCategory category2 = createCategory(2L);
        categories.add(category2);
        List<NewsCategoryResponse> categoriesResponse = new ArrayList<>();
        NewsCategoryResponse categoryResponse1 = createCategoryResponse(1L);
        categoriesResponse.add(categoryResponse1);
        NewsCategoryResponse categoryResponse2 = createCategoryResponse(2L);
        categoriesResponse.add(categoryResponse2);

        NewsCategoryListResponse newsCategoryListResponse = new NewsCategoryListResponse(categoriesResponse);

        Mockito.when(categoryService.findAll()).thenReturn(categories);
        Mockito.when(categoryMapper.newsCategoryListToNewsCategoryResponseList(categories)).thenReturn(newsCategoryListResponse);

        String actualResponse = mockMvc.perform(get("/api/v2/newsCategory"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/find_all_news_categories.json");

        Mockito.verify(categoryService, Mockito.times(1)).findAll();
        Mockito.verify(categoryMapper, Mockito.times(1)).newsCategoryListToNewsCategoryResponseList(categories);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenGetNewsCategoryById_thenReturnCategoryById() throws Exception {
        NewsCategory category = createCategory(1L);
        NewsCategoryResponse categoryResponse = createCategoryResponse(1L);

        Mockito.when(categoryService.findById(1L)).thenReturn(category);
        Mockito.when(categoryMapper.newsCategoryToResponse(category)).thenReturn(categoryResponse);

        String actualResponse = mockMvc.perform(get("/api/v2/newsCategory/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/find_newsCategory_by_id.json");

        Mockito.verify(categoryService, Mockito.times(1)).findById(1L);
        Mockito.verify(categoryMapper, Mockito.times(1)).newsCategoryToResponse(category);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenCreateCategory_thenReturnNewCategory() throws Exception {
        NewsCategory category = new NewsCategory();
        category.setCategory("category 1");
        NewsCategory createdCategory = createCategory(1L);
        NewsCategoryResponse categoryResponse = createCategoryResponse(1L);
        UpsertNewsCategoryRequest request = new UpsertNewsCategoryRequest("category 1");

        Mockito.when(categoryService.save(category)).thenReturn(createdCategory);
        Mockito.when(categoryMapper.requestToNewsCategory(request)).thenReturn(category);
        Mockito.when(categoryMapper.newsCategoryToResponse(createdCategory)).thenReturn(categoryResponse);

        String actualResponse = mockMvc.perform(post("/api/v2/newsCategory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/create_newCategory.json");

        Mockito.verify(categoryService, Mockito.times(1)).save(category);
        Mockito.verify(categoryMapper, Mockito.times(1)).requestToNewsCategory(request);
        Mockito.verify(categoryMapper, Mockito.times(1)).newsCategoryToResponse(createdCategory);


        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenUpdateNewsCategory_thenReturnUpdatedNewsCategory() throws Exception {
        UpsertNewsCategoryRequest request = new UpsertNewsCategoryRequest("category 2");
        NewsCategory updatedCategory = new NewsCategory(1L, "category 2", new ArrayList<>());
        NewsCategoryResponse categoryResponse = new NewsCategoryResponse(1L, "category 2");

        Mockito.when(categoryService.update(updatedCategory)).thenReturn(updatedCategory);
        Mockito.when(categoryMapper.requestToNewsCategory(1L, request)).thenReturn(updatedCategory);
        Mockito.when(categoryMapper.newsCategoryToResponse(updatedCategory)).thenReturn(categoryResponse);

        String actualResponse = mockMvc.perform(put("/api/v2/newsCategory/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/update_newsCategory_response.json");

        Mockito.verify(categoryService, Mockito.times(1)).update(updatedCategory);
        Mockito.verify(categoryMapper, Mockito.times(1)).requestToNewsCategory(1L, request);
        Mockito.verify(categoryMapper, Mockito.times(1)).newsCategoryToResponse(updatedCategory);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenDeleteNewsCategoryById_thenReturnStatusNoContent() throws Exception {
        NewsCategory category = createCategory(1L);
        mockMvc.perform(delete("/api/v2/newsCategory/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(categoryService, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenFindByIdNotExistedCategory_thenReturnError() throws Exception {
        Mockito.when(categoryService.findById(100L)).thenThrow(new EntityNotFoundException("Категория с ID 100 не найдена!"));

        var response = mockMvc.perform(get("/api/v2/newsCategory/100"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();
        response.setCharacterEncoding("UTF-8");

        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/newsCategory_by_id_is_not_found_response.json");

        Mockito.verify(categoryService, Mockito.times(1)).findById(100L);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenCreateNewsCategoryWithEmptyName_thenReturnError() throws Exception {

        var response = mockMvc.perform(post("/api/v2/newsCategory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpsertNewsCategoryRequest())))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        response.setCharacterEncoding("UTF-8");
        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/empty_newsCategory_response.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenFindAllComments_thenReturnAllComments() throws Exception {
        List<UserComment> comments = new ArrayList<>();
        comments.add(createComment(1L, createUser(1L), createNews(1L, createCategory(1L))));
        comments.add(createComment(2L, createUser(2L), createNews(2L, createCategory(2L))));

        List<UserCommentResponse> commentResponses = new ArrayList<>();
        commentResponses.add(createCommentResponse(1L, createUserResponse(1L), createNewsResponseForList(1L, createCategoryResponse(1L))));
        commentResponses.add(createCommentResponse(2L, createUserResponse(2L), createNewsResponseForList(2L, createCategoryResponse(2L))));


        UserCommentListResponse commentsListResponse = new UserCommentListResponse(commentResponses);

        Mockito.when(commentService.findAll()).thenReturn(comments);

        Mockito.when(commentsMapper.userCommentListToUserCommentResponseList(comments)).thenReturn(commentsListResponse);

        String actualResponse = mockMvc.perform(get("/api/v2/comments"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/find_all_comments_response.json");

        Mockito.verify(commentService, Mockito.times(1)).findAll();
        Mockito.verify(commentsMapper, Mockito.times(1)).userCommentListToUserCommentResponseList(comments);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenGetCommentById_thenReturnCommentById() throws Exception {
        UserComment comment = createComment(1L, createUser(1L), createNews(1L, createCategory(1L)));
        UserResponse userResponse1 = createUserResponse(1L);
        NewsResponseForList newsResponse = new NewsResponseForList(1L, "Author 1", createCategoryResponse(1L), "NewsText 1", "Title 1", 1L);
        UserCommentResponse commentResponse = createCommentResponse(1L, userResponse1, newsResponse);

        Mockito.when(commentService.findById(1L)).thenReturn(comment);
        Mockito.when(commentsMapper.userCommentToResponse(comment)).thenReturn(commentResponse);

        String actualResponse = mockMvc.perform(get("/api/v2/comments/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/find_comment_by_id_response.json");

        Mockito.verify(commentService, Mockito.times(1)).findById(1L);
        Mockito.verify(commentsMapper, Mockito.times(1)).userCommentToResponse(comment);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateComment_thenReturnNewComment() throws Exception {
        UserComment comment = new UserComment();
        comment.setComment("comment 1");
        comment.setNews(new News(1L, "Title 1", "NewsText 1", "Author 1",
                new NewsCategory(1L, "category 1", new ArrayList<>()), new ArrayList<>()));
        comment.setUser(new User(1L, "user 1", new ArrayList<>()));

        UserComment createdComment = createComment(1L, createUser(1L), createNews(1L, createCategory(1L)));
        UserCommentResponse commentResponse = createCommentResponse(1L, createUserResponse(1L), createNewsResponseForList(1L, createCategoryResponse(1L)));
        UpsertUserCommentRequest request = new UpsertUserCommentRequest(1L, 1L, "comment 1");

        Mockito.when(commentService.save(comment)).thenReturn(createdComment);
        Mockito.when(commentsMapper.requestToUserComment(request)).thenReturn(comment);
        Mockito.when(commentsMapper.userCommentToResponse(createdComment)).thenReturn(commentResponse);

        String actualResponse = mockMvc.perform(post("/api/v2/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/create_new_comment_response.json");

        Mockito.verify(commentService, Mockito.times(1)).save(comment);
        Mockito.verify(commentsMapper, Mockito.times(1)).requestToUserComment(request);
        Mockito.verify(commentsMapper, Mockito.times(1)).userCommentToResponse(createdComment);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenFindByIsNotExistedComment_thenReturnError() throws Exception {
        Mockito.when(commentService.findById(100L)).thenThrow(new EntityNotFoundException("Комментарий с ID 100 не найден!"));

        var response = mockMvc.perform(get("/api/v2/comments/100"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();

        response.setCharacterEncoding("UTF-8");

        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/comment_by_id_not_found.json");

        Mockito.verify(commentService, Mockito.times(1)).findById(100L);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateCommentWithEmptyComment_thenReturnError() throws Exception {
        var response = mockMvc.perform(post("/api/v2/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpsertUserCommentRequest())))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        response.setCharacterEncoding("UTF-8");
        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/empty_comment_response.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenDeleteCommentById_thenReturnStatusNoContent() throws Exception {
        mockMvc.perform(delete("/api/v2/comments/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(commentService, Mockito.times(1)).deleteById(1L);
    }
}


