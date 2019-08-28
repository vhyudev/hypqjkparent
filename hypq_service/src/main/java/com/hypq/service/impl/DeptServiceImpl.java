package com.hypq.service.impl;

import cn.hypq.utils.Page;
import com.hypq.dao.BaseDao;
import com.hypq.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeptServiceImpl implements DeptService {
    @Autowired
    BaseDao baseDao;

    @Override
    public List<Dept> findAll() {
        List<Dept> depts = baseDao.find("from Dept", Dept.class, null);
        return depts;
    }

    @Override
    public Page<Dept> findByPage(String pagenum ) {
        Page<Dept> page = new Page<>();
        page.setPageNo(Integer.valueOf(pagenum));
        page.setPageSize(5);
        page = baseDao.findPage("from Dept", page, Dept.class, null);
        return page;
    }

    @Override
    public Dept findById(String id) {
      List<Dept> list= baseDao.find("from Dept where id = ?" ,Dept.class,new String[]{id});
        return list.get(0);
    }

    @Override
    public void createDept(Dept dept) {
        baseDao.saveOrUpdate(dept);
    }

    @Override
    public void deleteById(String id) {
        baseDao.deleteById(Dept.class,id);
    }
}
