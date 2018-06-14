package com.jd.jr.pmock.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PageController {

    @RequestMapping("sys/{url}.html")
    public String sysPage(@PathVariable("url") String url){
        return url + ".html";
    }

}