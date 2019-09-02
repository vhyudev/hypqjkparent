package com.hypq.service.impl;

import cn.hypq.utils.Page;
import com.hypq.dao.BaseDao;
import com.hypq.domain.ContractProduct;
import com.hypq.service.ContractProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContractProductServiceImpl implements ContractProductService {
    @Autowired
    BaseDao baseDao;

    @Override
    public List<ContractProduct> findAll() {
        List<ContractProduct> depts = baseDao.find("from ContractProduct", ContractProduct.class, null);
        return depts;
    }

    @Override
    public Page<ContractProduct> findByPage(String pagenum,ContractProduct cp ) {
        Page<ContractProduct> page = new Page<>();
        page.setPageNo(Integer.valueOf(pagenum));
        page.setPageSize(5);
        page = baseDao.findPage("from ContractProduct where contract.id=?", page, ContractProduct.class, new String []{cp.getContract().getId()});
        return page;
    }

    @Override
    public ContractProduct findById(String id) {
      List<ContractProduct> list= baseDao.find("from ContractProduct where id = ?" ,ContractProduct.class,new String[]{id});
        return list.get(0);
    }

    @Override
    public void createContractProduct(ContractProduct contractProduct) {
        baseDao.saveOrUpdate(contractProduct);
    }

    @Override
    public void deleteById(String id) {
        baseDao.deleteById(ContractProduct.class,id);
    }
}
