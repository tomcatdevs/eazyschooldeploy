package com.eazybytes.example18.controller;

import com.eazybytes.example18.Model.EmailSubscribe;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class subscribeController {

//    private static final Logger log= LoggerFactory.getLogger(subscribeController.class);

    @RequestMapping(value = {"/subscribe"},method = RequestMethod.POST)
    public ModelAndView emailSubscription(EmailSubscribe emailSubscribe){
        log.info("REGISTERING EMAIL FOR NEWS SUBSCRIPTION");
        log.info(String.valueOf(emailSubscribe));
        return new ModelAndView("redirect:/home");
    }
}
