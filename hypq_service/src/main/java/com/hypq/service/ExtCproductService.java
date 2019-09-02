package com.hypq.service;

import cn.hypq.utils.Page;
import com.hypq.domain.ExtCproduct;

import java.util.List;

public interface ExtCproductService {
    List<ExtCproduct> findAll();

    Page<ExtCproduct> findByPage(String pagenum,ExtCproduct ep);

    ExtCproduct findById(String id);

    void createExtCproduct(ExtCproduct dept);

    void deleteById(String id);
}
