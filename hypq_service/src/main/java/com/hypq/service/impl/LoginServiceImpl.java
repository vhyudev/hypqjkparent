package com.hypq.service.impl;

import com.hypq.dao.BaseDao;
import com.hypq.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    BaseDao baseDao;

}
