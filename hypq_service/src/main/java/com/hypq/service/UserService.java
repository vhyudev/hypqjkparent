package com.hypq.service;

import cn.hypq.utils.Page;
import com.hypq.domain.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    Page<User> findByPage(String pagenum);

    User findById(String id);

    void createUser(User user);

    void deleteById(String id);

    List<User> find(String hql, Class<User> userClass, Object[] strings);
}
