package com.hypq.service.impl;

import cn.hypq.utils.Page;
import com.hypq.dao.BaseDao;
import com.hypq.domain.ExtCproduct;
import com.hypq.service.ExtCproductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExtCproductServiceImpl implements ExtCproductService {
    @Autowired
    BaseDao baseDao;

    @Override
    public List<ExtCproduct> findAll() {
        List<ExtCproduct> depts = baseDao.find("from ExtCproduct", ExtCproduct.class, null);
        return depts;
    }

    @Override
    public Page<ExtCproduct> findByPage(String pagenum, ExtCproduct ep) {
        Page<ExtCproduct> page = new Page<>();
        page.setPageNo(Integer.valueOf(pagenum));
        page.setPageSize(5);
        page = baseDao.findPage("from ExtCproduct where contractProduct.id=?", page, ExtCproduct.class, new String[]{ep.getContractProduct().getId()});
        return page;
    }

    @Override
    public ExtCproduct findById(String id) {
      List<ExtCproduct> list= baseDao.find("from ExtCproduct where id = ?" ,ExtCproduct.class,new String[]{id});
        return list.get(0);
    }

    @Override
    public void createExtCproduct(ExtCproduct dept) {
        baseDao.saveOrUpdate(dept);
    }

    @Override
    public void deleteById(String id) {
        baseDao.deleteById(ExtCproduct.class,id);
    }
}
