package com.hypq.service.impl;

import cn.hypq.utils.Page;
import com.hypq.dao.BaseDao;
import com.hypq.domain.Role;
import com.hypq.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    BaseDao baseDao;

    @Override
    public List<Role> findAll() {
        List<Role> Roles = baseDao.find("from Role", Role.class, null);
        return Roles;
    }

    @Override
    public Page<Role> findByPage(String pagenum ) {
        Page<Role> page = new Page<>();
        page.setPageNo(Integer.valueOf(pagenum));
        page.setPageSize(5);
        page = baseDao.findPage("from Role", page, Role.class, null);
        return page;
    }

    @Override
    public Role findById(String id) {
      List<Role> list= baseDao.find("from Role where id = ?" ,Role.class,new String[]{id});
      if(list==null){
          return null;
      }
        return list.get(0);
    }

    @Override
    public void createRole(Role Role) {
        baseDao.saveOrUpdate(Role);
    }


    @Override
    public void deleteById(String id) {
        baseDao.deleteById(Role.class,id);
    }
}
