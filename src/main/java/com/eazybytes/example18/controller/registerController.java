package com.eazybytes.example18.controller;

import com.eazybytes.example18.Model.Person;
import com.eazybytes.example18.Model.Roles;
import com.eazybytes.example18.service.PersonService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
@RequestMapping("public")
public class registerController {

    @Autowired
    private PersonService personService;

    private

    @RequestMapping(value = "/register",method = RequestMethod.GET) String doRegistration(Model model){
        model.addAttribute("person",new Person());
        return "register.html";
    }

    @RequestMapping(value = "/createUser",method = RequestMethod.POST)
    public String createUser(@Valid @ModelAttribute("person") Person person, Errors errors){
        if (errors.hasErrors()){
            return "register.html";
        }
//        System.out.println(person);
//        boolean registerStatus=false;
//        Boolean savedPerson=personService.registerPerson(person);
//        if (savedPerson){
//            log.info(String.valueOf(savedPerson)+" :: PERSON REGISTERED SUCCESSFULLY");
//        }else {
//            log.info(String.valueOf(savedPerson)+" :: FACING AN ERROR WHILE REGISTERING");
//        }
//        return "redirect:/login?register=true";=

        boolean savePerson=personService.registerPerson(person);
        if (savePerson) {
            return "redirect:/login?register=true";
        }
        return "register.html";
    }
}

