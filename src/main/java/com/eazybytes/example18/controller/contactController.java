package com.eazybytes.example18.controller;

import com.eazybytes.example18.Model.Contact;
import com.eazybytes.example18.service.ContactService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

//--------------------------------------------
//@Component
// ==> WEBSITE RUNS BUT viewPage NOT RETURN
//@RestController
//==> viewPage return + [json+xml]->restApi
//@Controller
//==> viewPage return
//--------------------------------------------

@Controller
public class contactController {

    private static Logger log = LoggerFactory.getLogger(contactController.class);

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = {"/contact"})
    public String displayContactPage(Model model){
        model.addAttribute("newContactEntity",new Contact());
        return "contact.html";
    }

//    @RequestMapping(value = {"/saveMsg"},method= RequestMethod.POST)
//    public ModelAndView saveContactMessage(
//                                @RequestParam String name,
//                                @RequestParam String mobileNum,
//                                @RequestParam String email,
//                                @RequestParam String subject,
//                                @RequestParam String message){
//        log.info("name :: "+name);
//        log.info("mobile :: "+mobileNum);
//        log.info("email :: "+email);
//        log.info("subject :: "+subject);
//        log.info("message :: "+message);
//
//        return new ModelAndView("redirect:/contact");
//    }

    @RequestMapping(value = {"/saveMsg"},method = RequestMethod.POST)
    public String saveContactMessage(@Valid @ModelAttribute("newContactEntity") Contact contact, Errors errors)
    {
        if (errors.hasErrors()){
            log.info("Contact form validation failed due to : "+errors.toString());
            return "contact.html";
        }
        boolean response = contactService.savedContactToDB(contact);
        if (response)
            log.info("USER CONTACT FORM SUBMITTED SUCCESSFULLY...");
        else
            log.info("SOMETHING WENT WRONG TRY AGAIN ?");
        return "redirect:/contact";
    }

    @RequestMapping("/displayMessages/page/{pageNum}")
    public ModelAndView displayMessages(Model model,
                                        @PathVariable(name = "pageNum") int pageNum,
                                        @RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir){
//        ArrayList<Contact> contactMessages = contactService.findAllOpenQueryMessages();

        Page<Contact> msgPage = contactService.findAllOpenQueryMessages(pageNum,sortField,sortDir);
        ModelAndView modelAndView = new ModelAndView("messages.html");

        List<Contact> contactMessages = msgPage.getContent();
        model.addAttribute("currentPage",pageNum);
        model.addAttribute("totalPages",msgPage.getTotalPages());
        model.addAttribute("totalMsg",msgPage.getTotalElements());
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",sortDir.equals("asc") ? "desc":"asc");

        modelAndView.addObject("contactMessages",contactMessages);
        return modelAndView;
    }

    @RequestMapping(value = "/closeMsg",method = RequestMethod.GET)
    public String closeMsg(@RequestParam int id){
            contactService.updateMsgStatus(id);
        return "redirect:/displayMessages/page/1?sortField=name&sortDir=desc";
    }
}
