package com.hypq.service;

import cn.hypq.utils.Page;
import com.hypq.domain.Module;

import java.util.List;

public interface ModuleService {
    List<Module> findAll();

    Page<Module> findByPage(String pagenum);

    Module findById(String id);

    void createModule(Module role);

    void deleteById(String id);
}
