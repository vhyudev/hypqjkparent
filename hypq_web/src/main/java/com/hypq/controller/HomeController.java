package com.hypq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/home_title")
    public String homeTitle(){
        return "home/title";
    }
    @RequestMapping("/home_toleft")
    public String homeToleft(String moduleName){
        return moduleName+"/left";
    }
    @RequestMapping("/home_tomain")
    public String homeTomain(String moduleName){
        return moduleName+"/main";
    }
}
