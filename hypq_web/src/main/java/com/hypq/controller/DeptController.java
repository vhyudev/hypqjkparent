package com.hypq.controller;

import cn.hypq.utils.Page;
import com.hypq.domain.Dept;
import com.hypq.service.DeptService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/sysadmin")
public class DeptController {
    @Autowired
    DeptService deptService;

    //分页查询部门信息
    @RequestMapping("/deptAction_list")
    public String deptAction_list(Model mode,String pagenum){
        if( StringUtils.isBlank(pagenum)){
            pagenum="1";
        }
        Page<Dept> page=deptService.findByPage(pagenum);
        page.setUrl("/sysadmin/deptAction_list");
        mode.addAttribute("results",page.getResults());
        mode.addAttribute("link",page.getLinks());
        return "sysadmin/dept/jDeptList";
    }

    //查看部门详情
    @RequestMapping("/deptAction_toview")
    public String deptAction_toview(Model model,String id){
       Dept dept= deptService.findById(id);
        model.addAttribute("dept",dept);
        return "sysadmin/dept/jDeptView";
    }

    //到新增部门页面 deptAction_tocreate
    @RequestMapping("/deptAction_tocreate")
    public String deptAction_tocreate(Model model){

        List<Dept> list=deptService.findAll();
        model.addAttribute("depts",list);
        return "sysadmin/dept/jDeptCreate";
    }
    //新增部门或者修改部门 deptAction_insert
    @RequestMapping("/deptAction_insert")
    public String deptAction_insert(String parentId,String deptName,String id){
        Dept dept=null;
        Dept parentDept=null;
        if(StringUtils.isBlank(id)){
             dept = new Dept();
        }else {
            dept=deptService.findById(id);
        }
        if(!StringUtils.isBlank(parentId)){
             parentDept = deptService.findById(parentId);
        }


        dept.setParent(parentDept);
        dept.setDeptName(deptName);
        deptService.createDept(dept);

        return "redirect:deptAction_list";
    }

  //部门修改  deptAction_toupdate
  @RequestMapping("/deptAction_toupdate")
  public String deptAction_toupdate(Model model ,String id ){
      String parentId=null;
        List<Dept> list=deptService.findAll();
       for(int i=0;i<list.size();i++){
           if (list.get(i).getId().equals(id)){
               list.remove(i);
           }
       }
        Dept dept=deptService.findById(id);
        try {
             parentId=dept.getParent().getId();
        }catch (Exception e){
            parentId=null;
        }

        model.addAttribute("depts",list);
        model.addAttribute("parentId",parentId);
      model.addAttribute("dept",dept);
      return "sysadmin/dept/jDeptUpdate";
  }
    //部门删除 deptAction_delete  递归删除
    @RequestMapping("/deptAction_delete")
    public String deptAction_delete(String id){
       deleteAll(id);
        return "redirect:deptAction_list";
    }
    public void deleteAll(String id){
        List<Dept> all = deptService.findAll();
            for(Dept dept:all){
                if(dept.getParent()!=null){
                    if(dept.getParent().getId().equals(id)){
                        deleteAll(dept.getId());
                    }
                }

            }
        deptService.deleteById( id);
    }
}
