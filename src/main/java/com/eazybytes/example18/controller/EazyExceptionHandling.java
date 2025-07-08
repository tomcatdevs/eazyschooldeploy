package com.eazybytes.example18.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(annotations = Controller.class)
public class EazyExceptionHandling {

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception ex){
        String errorMsg=null;
        ModelAndView errorPage = new ModelAndView();
        if (ex.getMessage()!=null){
            errorMsg= ex.getMessage();
        } else if (ex.getCause().toString()!=null) {
            errorMsg=ex.getCause().toString();
        }
        else if (ex!=null){
            errorMsg=ex.getMessage();
        }
        errorPage.setViewName("error");
        errorPage.addObject("errormsg",ex.getMessage());
        return errorPage;
    }
}
