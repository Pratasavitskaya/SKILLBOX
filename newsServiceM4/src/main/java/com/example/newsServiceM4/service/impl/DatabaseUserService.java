package com.example.newsServiceM4.service.impl;

import com.example.newsServiceM4.exception.EntityNotFoundException;
import com.example.newsServiceM4.model.User;
import com.example.newsServiceM4.repository.DatabaseUserRepository;
import com.example.newsServiceM4.service.UserService;
import com.example.newsServiceM4.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseUserService implements UserService {

    private final DatabaseUserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Пользователь с ID:{0} не найден!", id)));
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        User existedUser = findById(user.getId());
        BeanUtils.copyNonNullProperties(user, existedUser);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByIn(List<Long> ids) {
        userRepository.deleteAllById(ids);
    }
}
