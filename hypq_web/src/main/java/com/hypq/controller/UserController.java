package com.hypq.controller;

import cn.hypq.utils.Encrypt;
import cn.hypq.utils.Page;
import com.hypq.domain.Dept;
import com.hypq.domain.Role;
import com.hypq.domain.User;
import com.hypq.service.DeptService;
import com.hypq.service.RoleService;
import com.hypq.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Controller
@RequestMapping("/sysadmin")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    DeptService deptService;
    @Autowired
    RoleService roleService;
    //分页查询用户信息
    @RequestMapping("/userAction_list")
    public String userAction_list(Model mode,String pagenum){
        if( StringUtils.isBlank(pagenum)){
            pagenum="1";
        }
        Page<User> page=userService.findByPage(pagenum);
        page.setUrl("/sysadmin/userAction_list");
        mode.addAttribute("results",page.getResults());
        mode.addAttribute("link",page.getLinks());
        return "sysadmin/user/jUserList";
    }

    //查看用户详情
    @RequestMapping("/userAction_toview")
    public String userAction_toview(Model model,String id){
       User user= userService.findById(id);
        model.addAttribute("user",user);
        return "sysadmin/user/jUserView";
    }

    //到新增用户页面 userAction_tocreate
    @RequestMapping("/userAction_tocreate")
    public String userAction_tocreate(Model model){
        List<User> userList=userService.findAll();
        List<Dept> deptList=deptService.findAll();
        model.addAttribute("userList",userList);

        model.addAttribute("deptList",deptList);
        return "sysadmin/user/jUserCreate";
    }
    //新增用户
    @RequestMapping("/userAction_insert")
    public String userAction_insert(User user,String deptId,String managerId){
        Dept dept = deptService.findById(deptId);
        User manager=userService.findById(managerId);
        String  id= UUID.randomUUID().toString();
        user.setId(id);
        user.getUserinfo().setId(id);
        user.getUserinfo().setManager(manager);
        user.setDept(dept);

        //设置初始密码
        user.setPassword(Encrypt.md5("123456",user.getUserName()));
       userService.createUser(user);
        return "redirect:userAction_list";
    }

  //用户修改  userAction_toupdate
  @RequestMapping("/userAction_toupdate")
  public String userAction_toupdate(Model model ,String id ){
      User user = userService.findById(id);
      String deptId = user.getDept().getId();
      List<Dept> all = deptService.findAll();
      model.addAttribute("user",user);
      model.addAttribute("depts",all);
      model.addAttribute("deptId",deptId);
      return "sysadmin/user/jUserUpdate";
  }
    //用户删除 userAction_delete  递归删除
    @RequestMapping("/userAction_delete")
    public String userAction_delete(String[] id){
        for (String si:id){
            userService.deleteById(si);
        }

        return "redirect:userAction_list";
    }


    //用户修改  userAction_toupdate
    @RequestMapping("/userAction_update")
    public String userAction_update(String id,String deptId,String userName,Integer state ){
        User user = userService.findById(id);
        Dept dept = deptService.findById(deptId);
        user.setUserName(userName);
        user.setState(state);
        user.setDept(dept);
        userService.createUser(user);
        return "redirect:userAction_list";
    }

    /*public void deleteAll(String id){
        List<User> all = userService.findAll();
            for(User user:all){
                if(user.getParent()!=null){
                    if(user.getParent().getId().equals(id)){
                        deleteAll(user.getId());
                    }
                }

            }
        userService.deleteById( id);
    }*/

    @RequestMapping("/userAction_torole")
    public String userAction_torole(Model model,String id){

        //当前选中用户userInfo
         User userInfo = userService.findById(id);
        //所有角色的列表roleList
        List<Role> roleList = roleService.findAll();
        //当前用户下的角色名字的字符串userRoleStr
        Set<Role> roles = userInfo.getRoles();
        StringBuilder sb=new StringBuilder();
        for(Role role: roles){
            sb.append("-");
            sb.append(role.getName());
        }
        model.addAttribute("userInfo",userInfo);
        model.addAttribute("roleList",roleList);
        model.addAttribute("userRoleStr",sb.toString());
        System.out.println(sb.toString());
        return "sysadmin/user/jUserRole";
    }


    @RequestMapping("/userAction_role")
    public String userAction_role(Model model,String id, String[] roleIds){

        //当前选中用户userInfo
        User userInfo = userService.findById(id);
       //选中的角色添加到set集合
        Set<Role> set=new HashSet<>();
        for(String roleid:roleIds){
            Role role = roleService.findById(roleid);
            set.add(role);
        }
        userInfo.setRoles(set);
        //保存
        userService.createUser(userInfo);
        return "redirect:userAction_list";
    }
}
