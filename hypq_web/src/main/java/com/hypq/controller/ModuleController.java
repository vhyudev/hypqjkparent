package com.hypq.controller;

import cn.hypq.utils.Page;
import com.hypq.domain.Module;
import com.hypq.service.DeptService;
import com.hypq.service.ModuleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/sysadmin")
public class ModuleController {
    @Autowired
    ModuleService moduleService;
    @Autowired
    DeptService deptService;
    //分页查询角色信息
    @RequestMapping("/moduleAction_list")
    public String moduleAction_list(Model mode,String pagenum){
        if( StringUtils.isBlank(pagenum)){
            pagenum="1";
        }
        Page<Module> page=moduleService.findByPage(pagenum);
        page.setUrl("/sysadmin/moduleAction_list");
        mode.addAttribute("results",page.getResults());
        mode.addAttribute("link",page.getLinks());
        return "sysadmin/module/jModuleList";
    }

    //查看角色详情
    @RequestMapping("/moduleAction_toview")
    public String moduleAction_toview(Model model,String id){
       Module module= moduleService.findById(id);
        model.addAttribute("module",module);
        return "sysadmin/module/jModuleView";
    }

    //到新增角色页面 moduleAction_tocreate
    @RequestMapping("/moduleAction_tocreate")
    public String moduleAction_tocreate(Model model){
       
        return "sysadmin/module/jModuleCreate";
    }
    //新增角色
    @RequestMapping("/moduleAction_insert")
    public String moduleAction_insert(Module module){
        moduleService.createModule(module);
        return "redirect:moduleAction_list";
    }

  //到角色修改界面  moduleAction_toupdate
  @RequestMapping("/moduleAction_toupdate")
  public String moduleAction_toupdate(Model model ,String id ){
      Module byId = moduleService.findById(id);
        model.addAttribute("module",byId);
      return "sysadmin/module/jModuleUpdate";
  }
    //角色删除 moduleAction_delete
    @RequestMapping("/moduleAction_delete")
    public String moduleAction_delete(String[] id){
        for (String si:id){
            moduleService.deleteById(si);
        }

        return "redirect:moduleAction_list";
    }


    //角色修改  moduleAction_toupdate
    @RequestMapping("/moduleAction_update")
    public String moduleAction_update(Module module){


        moduleService.createModule(module);
        return "redirect:moduleAction_list";
    }

    /*public void deleteAll(String id){
        List<Module> all = moduleService.findAll();
            for(Module module:all){
                if(module.getParent()!=null){
                    if(module.getParent().getId().equals(id)){
                        deleteAll(module.getId());
                    }
                }

            }
        moduleService.deleteById( id);
    }*/
}
