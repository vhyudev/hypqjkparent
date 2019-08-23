package com.hypq.service;

import cn.hypq.utils.Page;
import com.hypq.domain.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> findAll();

    Page<Dept> findByPage( String pagenum);

    Dept findById(String id);

    void createDept(Dept dept);

    void deleteById(String id);
}
