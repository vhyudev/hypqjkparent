package com.hypq.service.impl;

import cn.hypq.utils.Page;
import com.hypq.dao.BaseDao;
import com.hypq.domain.Module;
import com.hypq.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    BaseDao baseDao;

    @Override
    public List<Module> findAll() {
        List<Module> Modules = baseDao.find("from Module", Module.class, null);
        return Modules;
    }

    @Override
    public Page<Module> findByPage(String pagenum ) {
        Page<Module> page = new Page<>();
        page.setPageNo(Integer.valueOf(pagenum));
        page.setPageSize(5);
        page = baseDao.findPage("from Module", page, Module.class, null);
        return page;
    }

    @Override
    public Module findById(String id) {
      List<Module> list= baseDao.find("from Module where id = ?" ,Module.class,new String[]{id});
      if(list==null){
          return null;
      }
        return list.get(0);
    }

    @Override
    public void createModule(Module Module) {
        baseDao.saveOrUpdate(Module);
    }


    @Override
    public void deleteById(String id) {
        baseDao.deleteById(Module.class,id);
    }
}
