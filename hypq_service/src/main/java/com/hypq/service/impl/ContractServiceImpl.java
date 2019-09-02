package com.hypq.service.impl;

import cn.hypq.utils.Page;
import com.hypq.dao.BaseDao;
import com.hypq.domain.Contract;
import com.hypq.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {
    @Autowired
    BaseDao baseDao;

    @Override
    public List<Contract> findAll() {
        List<Contract> depts = baseDao.find("from Contract", Contract.class, null);
        return depts;
    }

    @Override
    public Page<Contract> findByPage(String pagenum ) {
        Page<Contract> page = new Page<>();
        page.setPageNo(Integer.valueOf(pagenum));
        page.setPageSize(5);
        page = baseDao.findPage("from Contract", page, Contract.class, null);
        return page;
    }

    @Override
    public Contract findById(String id) {
      List<Contract> list= baseDao.find("from Contract where id = ?" ,Contract.class,new String[]{id});
        return list.get(0);
    }

    @Override
    public void createContract(Contract dept) {

        baseDao.saveOrUpdate(dept);
    }

    @Override
    public void deleteById(String id) {
        baseDao.deleteById(Contract.class,id);
    }
}
