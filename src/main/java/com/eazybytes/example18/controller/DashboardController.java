package com.eazybytes.example18.controller;

import com.eazybytes.example18.Model.Person;
import com.eazybytes.example18.Repository.PersonRepo;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class DashboardController {

    @Autowired
    private PersonRepo personRepo;

    @Value("${eazyschool.contact.pageSize}")
    private int pageSize;

    @Value("${eazyschool.contact.successMsg}")
    private String contact;

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session) {

        /*  TO CUSTOM CREATED ERROR TO SHOW ERROR PAGE TO END USER
        *   it will redirect or call ControllerAdvice's ExceptionHandler method to handle Error message.
        * */
//        throw new RuntimeException("Something went wrong Bro !!!, Please contact your site Administrator @prateekarya100@gmail.com");


        Person person=personRepo.readByEmail(authentication.getName());
        model.addAttribute("username",person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString().toUpperCase());
        session.setAttribute("loggedInProfileInfo",person);
        logMessages();
        return "dashboard.html";
    }

    public void logMessages(){
        log.info("Dashboard log info is working");
        log.error("Dashboard log error is working");
        log.debug("Dashboard log debug is working");
        log.trace("Dashboard log trace is working");

        log.info("pageSize = "+pageSize);
        log.info("contact = "+contact);
    }
}
