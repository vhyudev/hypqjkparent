package com.hypq.controller;

import cn.hypq.utils.Page;
import com.hypq.domain.Dept;
import com.hypq.domain.Module;
import com.hypq.domain.Role;
import com.hypq.service.DeptService;
import com.hypq.service.ModuleService;
import com.hypq.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Controller
@RequestMapping("/sysadmin")
public class RoleController {
    @Autowired
    RoleService roleService;
    @Autowired
    DeptService deptService;
    @Autowired
    ModuleService moduleService;
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
    @RequestMapping("/roleAction_tomodule")
    public String roleAction_tomodule(Model model,String id){
        Role role = roleService.findById(id);
        model.addAttribute("role",role);

        return "sysadmin/role/jRoleModule";
    }

    @RequestMapping("/roleAction_roleModuleJsonStr")
    public void roleAction_roleModuleJsonStr(HttpServletResponse response, String id){
        Role role = roleService.findById(id);
        //获取当前角色拥有的模块节点
        Set<Module> currentModules = role.getModules();
        //获取所有的模块节点
        List<Module> modules = moduleService.findAll();
        //拼接json选中当前模块的节点
        StringBuilder sb= new StringBuilder();
        sb.append("[");
        for (int i=0;i<modules.size()-1;i++){
            sb.append("{");
            sb.append("\"id\":").append("\"").append(modules.get(i).getId()).append("\"").append(",");
            sb.append("\"pId\":").append("\"").append(modules.get(i).getParentId()).append("\"").append(",");
            sb.append("\"name\":").append("\"").append(modules.get(i).getName()).append("\"").append(",");
            if (currentModules.contains(modules.get(i))){
                sb.append("\"checked\":").append("\"true\"");
            }else {
                sb.append("\"checked\":").append("\"false\"");
            }
            sb.append("}");
            if(i!=modules.size()-1){
                sb.append(",");
            }
        }

        sb.append("]");
        System.out.println(sb.toString());
        response.setContentType("text/html;charset=UTF-8");
        try {
            response.getWriter().write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @RequestMapping("/roleAction_module")
    public String roleAction_module(String moduleIds,String id){
        Role role = roleService.findById(id);
        Set<Module> set = new HashSet<>();
        String[] split = moduleIds.split(",");
        if (moduleIds!=null&&!"".equals(moduleIds)){
            for(String mid:split){
                Module byId = moduleService.findById(mid);
                set.add(byId);
            }
        }
        role.setModules(set);
        roleService.createRole(role);

        return "redirect:roleAction_list";
    }
}



