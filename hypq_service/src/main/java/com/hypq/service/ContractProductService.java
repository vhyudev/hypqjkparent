package com.hypq.service;

import cn.hypq.utils.Page;
import com.hypq.domain.ContractProduct;

import java.util.List;

public interface ContractProductService {
    List<ContractProduct> findAll();

    Page<ContractProduct> findByPage(String pagenum,ContractProduct cp);

    ContractProduct findById(String id);

    void createContractProduct(ContractProduct contractProduct);

    void deleteById(String id);
}
