package com.eazybytes.example18.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
public class myRestController {

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    public String getPage(){
        return "getpage.html";
    }
}
