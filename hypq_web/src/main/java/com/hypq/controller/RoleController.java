package com.hypq.controller;

import cn.hypq.utils.Page;
import com.hypq.domain.Dept;
import com.hypq.domain.Role;
import com.hypq.service.DeptService;
import com.hypq.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/sysadmin")
public class RoleController {
    @Autowired
    RoleService roleService;
    @Autowired
    DeptService deptService;
    //分页查询角色信息
    @RequestMapping("/roleAction_list")
    public String roleAction_list(Model mode,String pagenum){
        if( StringUtils.isBlank(pagenum)){
            pagenum="1";
        }
        Page<Role> page=roleService.findByPage(pagenum);
        page.setUrl("/sysadmin/roleAction_list");
        mode.addAttribute("results",page.getResults());
        mode.addAttribute("link",page.getLinks());
        return "sysadmin/role/jRoleList";
    }

    //查看角色详情
    @RequestMapping("/roleAction_toview")
    public String roleAction_toview(Model model,String id){
       Role role= roleService.findById(id);
        model.addAttribute("role",role);
        return "sysadmin/role/jRoleView";
    }

    //到新增角色页面 roleAction_tocreate
    @RequestMapping("/roleAction_tocreate")
    public String roleAction_tocreate(Model model){
       
        return "sysadmin/role/jRoleCreate";
    }
    //新增角色
    @RequestMapping("/roleAction_insert")
    public String roleAction_insert(Role role){
        roleService.createRole(role);
        return "redirect:roleAction_list";
    }

  //到角色修改界面  roleAction_toupdate
  @RequestMapping("/roleAction_toupdate")
  public String roleAction_toupdate(Model model ,String id ){
      Role byId = roleService.findById(id);
        model.addAttribute("role",byId);
      return "sysadmin/role/jRoleUpdate";
  }
    //角色删除 roleAction_delete
    @RequestMapping("/roleAction_delete")
    public String roleAction_delete(String[] id){
        for (String si:id){
            roleService.deleteById(si);
        }

        return "redirect:roleAction_list";
    }


    //角色修改  roleAction_toupdate
    @RequestMapping("/roleAction_update")
    public String roleAction_update(Role role){


        roleService.createRole(role);
        return "redirect:roleAction_list";
    }

    /*public void deleteAll(String id){
        List<Role> all = roleService.findAll();
            for(Role role:all){
                if(role.getParent()!=null){
                    if(role.getParent().getId().equals(id)){
                        deleteAll(role.getId());
                    }
                }

            }
        roleService.deleteById( id);
    }*/
}
