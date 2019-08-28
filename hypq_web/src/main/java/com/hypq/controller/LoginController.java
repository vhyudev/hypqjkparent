package com.hypq.controller;

import com.hypq.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller

public class LoginController {
    @Autowired
    LoginService loginService;
    @RequestMapping("/login")
    public String ToLoginPage(Date aa){
        System.out.println(aa);
        return "home/fmain";
    }
}
