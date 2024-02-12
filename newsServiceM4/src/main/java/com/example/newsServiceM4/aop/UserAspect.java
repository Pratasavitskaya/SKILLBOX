package com.example.newsServiceM4.aop;

import com.example.newsServiceM4.exception.EntityNotFoundException;
import com.example.newsServiceM4.exception.UpdateStateException;
import com.example.newsServiceM4.model.News;
import com.example.newsServiceM4.model.UserComment;
import com.example.newsServiceM4.service.CommentService;
import com.example.newsServiceM4.service.NewsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Objects;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;


@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class UserAspect {

    private final NewsService databaseNewsService;

    private final CommentService databaseCommentService;

    @Before("@annotation(Loggable) && args(news,..)")
    public void logBefore(News news) throws UpdateStateException {

      News newsInBase =  databaseNewsService.findById(news.getId());
       if (Objects.equals(newsInBase.getAuthor(), news.getAuthor()))
           log.info("Author is ok");
       else {
           log.info("Invalid author!");
           throw new EntityNotFoundException("Редактировать новость может только тот, кто ее создал!");
       }

    }
    @Before("@annotation(Loggable) && args(userComment,..)")
    public void logBefore(UserComment userComment) throws UpdateStateException {

        UserComment commentInBase =  databaseCommentService.findById(userComment.getId());
        if (Objects.equals(commentInBase.getUser().getId(), userComment.getUser().getId()))
            log.info("User is ok");
        else {
            log.info("Invalid user!");
            throw new EntityNotFoundException("Редактировать комментарий может только тот, кто его создал!");
        }

    }


}
