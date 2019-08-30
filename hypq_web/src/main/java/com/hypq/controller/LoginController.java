package com.hypq.controller;

import cn.hypq.utils.UtilFuns;
import com.hypq.domain.User;
import com.hypq.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller

public class LoginController {
    @Autowired
    LoginService loginService;
    @RequestMapping("/login")
    public String Login(String username, String password, HttpServletRequest request){
        if(UtilFuns.isEmpty(username)){
            return "sysadmin/login/login";
        }

        try {
            //1.得到Subject
            Subject subject = SecurityUtils.getSubject();
            //2.调用登录方法
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);//当这一代码执行时，就会自动跳入到AuthRealm中认证方法

            //3.登录成功时，就从Shiro中取出用户的登录信息
            User user = (User) subject.getPrincipal();

            //4.将用户放入session域中
         /*   request.getSession().put(SysConstant.CURRENT_USER_INFO, user);*/
         request.getSession().setAttribute("current_user_info",user);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorInfo", "对不起，用户名或密码错误！");
            return "sysadmin/login/login";
        }

        return "home/fmain";
    }
    //退出
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("current_user_info");		//删除session

        return "logout";
    }
}
