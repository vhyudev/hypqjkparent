package com.hypq.service.impl;

import cn.hypq.utils.Page;
import com.hypq.dao.BaseDao;
import com.hypq.domain.Factory;
import com.hypq.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FactoryServiceImpl implements FactoryService {
    @Autowired
    BaseDao baseDao;

    @Override
    public List<Factory> findAll() {
        List<Factory> depts = baseDao.find("from Factory", Factory.class, null);
        return depts;
    }

    @Override
    public Page<Factory> findByPage(String pagenum ) {
        Page<Factory> page = new Page<>();
        page.setPageNo(Integer.valueOf(pagenum));
        page.setPageSize(5);
        page = baseDao.findPage("from Factory", page, Factory.class, null);
        return page;
    }

    @Override
    public Factory findById(String id) {
      List<Factory> list= baseDao.find("from Factory where id = ?" ,Factory.class,new String[]{id});
        return list.get(0);
    }

    @Override
    public void createFactory(Factory dept) {
        baseDao.saveOrUpdate(dept);
    }

    @Override
    public void deleteById(String id) {
        baseDao.deleteById(Factory.class,id);
    }
}
