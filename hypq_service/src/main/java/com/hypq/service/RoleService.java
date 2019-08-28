package com.hypq.service;

import cn.hypq.utils.Page;
import com.hypq.domain.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    Page<Role> findByPage(String pagenum);

    Role findById(String id);

    void createRole(Role role);

    void deleteById(String id);
}
