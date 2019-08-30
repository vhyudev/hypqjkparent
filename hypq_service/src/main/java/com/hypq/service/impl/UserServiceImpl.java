package com.hypq.service.impl;

import cn.hypq.utils.Page;
import com.hypq.dao.BaseDao;
import com.hypq.domain.User;
import com.hypq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userServiceImpl")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    BaseDao baseDao;
    //邮件发送
    @Autowired
    private SimpleMailMessage mailMessage;

    @Autowired
    private JavaMailSender mailSender;
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
    public void createUser(final User user) {

        baseDao.saveOrUpdate(user);
        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                mailMessage.setSubject("入职提醒");
                mailMessage.setText("欢迎您加入本集团，您的用户名:"+user.getUserName()+",初始密码："+123456);
                mailMessage.setTo(user.getUserinfo().getEmail());
                mailSender.send(mailMessage);
            }
        });
        th.start();
    }


    @Override
    public void deleteById(String id) {
        baseDao.deleteById(User.class,id);
    }

    @Override
    public List<User> find(String hql, Class<User> userClass, Object[] strings) {
        return baseDao.find(hql,userClass,strings);
    }
}
