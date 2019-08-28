package com.hypq.service.impl;

import cn.hypq.utils.Page;
import com.hypq.dao.BaseDao;
import com.hypq.domain.User;
import com.hypq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    BaseDao baseDao;

    @Override
    public List<User> findAll() {
        List<User> Users = baseDao.find("from User", User.class, null);
        return Users;
    }

    @Override
    public Page<User> findByPage(String pagenum ) {
        Page<User> page = new Page<>();
        page.setPageNo(Integer.valueOf(pagenum));
        page.setPageSize(5);
        page = baseDao.findPage("from User", page, User.class, null);
        return page;
    }

    @Override
    public User findById(String id) {
      List<User> list= baseDao.find("from User where id = ?" ,User.class,new String[]{id});
      if(list==null){
          return null;
      }
        return list.get(0);
    }

    @Override
    public void createUser(User User) {
        baseDao.saveOrUpdate(User);
    }


    @Override
    public void deleteById(String id) {
        baseDao.deleteById(User.class,id);
    }
}
