package com.eazybytes.example18.controller;

import com.eazybytes.example18.Model.Address;
import com.eazybytes.example18.Model.Contact;
import com.eazybytes.example18.Model.Person;
import com.eazybytes.example18.Model.Profile;
import com.eazybytes.example18.Repository.PersonRepo;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller("profileControllerBean")
public class profileController {

    @Autowired
    private PersonRepo personRepo;

    @RequestMapping("/displayProfile")
    public ModelAndView displayProfile(Model model, HttpSession session){
        Profile profile=new Profile();
        Person person = (Person) session.getAttribute("loggedInProfileInfo");

        profile.setName(person.getName());
        profile.setEmail(person.getEmail());
        profile.setMobileNumber(person.getMobileNumber());

        if (person.getAddress() !=null && person.getPersonId()>0) {
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZipCode());
        }
        return new ModelAndView("profile","profile",profile);
    }

    @RequestMapping(value = "/updateProfile",method = RequestMethod.POST)
    public String updateProfile(@Valid @ModelAttribute Profile profile,Errors errors,HttpSession session){
        if (errors.hasErrors()){
            return "profile.html";
        }
        Person person = (Person) session.getAttribute("loggedInProfileInfo");
        System.out.println(person);
        System.out.println(profile);

        person.setName(profile.getName());
        person.setEmail(profile.getEmail());
        person.setMobileNumber(profile.getMobileNumber());

        if (person.getAddress() == null || !(person.getAddress().getAddressId()>0)){
            person.setAddress(new Address());
        }
        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setAddress2(profile.getAddress2());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setState(profile.getState());
        person.getAddress().setZipCode(profile.getZipCode());

//      PERSON ENTITY UPDATE AGAIN, USING SAVE REPOSITORY METHOD
        Person updatePerson = personRepo.save(person);
        session.setAttribute("loggedInProfileInfo",person);
        return "redirect:/dashboard";
    }
}
