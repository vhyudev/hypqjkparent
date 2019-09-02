package com.hypq.service;

import cn.hypq.utils.Page;
import com.hypq.domain.Contract;

import java.util.List;

public interface ContractService {
    List<Contract> findAll();

    Page<Contract> findByPage(String pagenum);

    Contract findById(String id);

    void createContract(Contract dept);

    void deleteById(String id);
}
