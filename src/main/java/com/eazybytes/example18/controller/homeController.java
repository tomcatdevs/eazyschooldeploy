package com.eazybytes.example18.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class homeController {

//    @RequestMapping(value = {"","/","home"})
//    public String ecommerceHomePage() {
//        return "ecommerce/pages/index.html";
//    }

//    @RequestMapping(value = {"","/","home"})
//    public String displayHomePage(Model model) {
//        model.addAttribute("username","Prateek Arya CognizantTech");
//        return "home.html";
//    }

    @RequestMapping(value = {"","/","home"} )
    public String displayHome(){
        return "home.html";
    }
}
