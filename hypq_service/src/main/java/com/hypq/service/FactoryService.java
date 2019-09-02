package com.hypq.service;

import cn.hypq.utils.Page;
import com.hypq.domain.Factory;

import java.util.List;

public interface FactoryService {
    List<Factory> findAll();

    Page<Factory> findByPage(String pagenum);

    Factory findById(String id);

    void createFactory(Factory dept);

    void deleteById(String id);
}
