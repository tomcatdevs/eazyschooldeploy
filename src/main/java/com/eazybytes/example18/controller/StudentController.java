package com.eazybytes.example18.controller;

import com.eazybytes.example18.Model.Courses;
import com.eazybytes.example18.Model.EazyClass;
import com.eazybytes.example18.Model.Person;
import com.eazybytes.example18.Repository.CoursesRepository;
import com.eazybytes.example18.Repository.PersonRepo;
import com.eazybytes.example18.Repository.eazyClassRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private com.eazybytes.example18.Repository.eazyClassRepository eazyClassRepository;

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private CoursesRepository coursesRepository;

    //    FOR CLASSES
    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model){
        ArrayList<EazyClass> eazyClasses = (ArrayList<EazyClass>) eazyClassRepository.findAll();
        ModelAndView modelAndView=new ModelAndView("classes.html");
        modelAndView.addObject("eazyClasses",eazyClasses);
        modelAndView.addObject("eazyClass",new EazyClass());
        return modelAndView;
    }
    @RequestMapping("/displayStudents")
    public ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession session,
                                        @RequestParam(value = "error",required = false) String error){
        String errorMessage=null;
        ModelAndView modelAndView= new ModelAndView("students.html");
        Optional<EazyClass> eazyClass = eazyClassRepository.findById(classId);
        modelAndView.addObject("eazyClass",eazyClass.get());
        modelAndView.addObject("person",new Person());
        session.setAttribute("eazyClass",eazyClass.get());
        if (error != null){
            errorMessage = "Sorry !!! , This student is not registered with us.";
            modelAndView.addObject("errorMessage",errorMessage);
        }
        return modelAndView;
    }
    @RequestMapping("/displayCourses")
    public ModelAndView displayCourses(Model model){
        ModelAndView modelAndView=new ModelAndView("courses_secure");
//        ArrayList<Courses> courses = (ArrayList<Courses>) coursesRepository.findAll();

//        STATIC SORTING
//        ArrayList<Courses> courses = coursesRepository.findByOrderByNameDesc();
//        ArrayList<Courses> courses = coursesRepository.findByOrderByName();

//      DYNAMIC SORTING based on name and fees only
        ArrayList<Courses> courses = (ArrayList<Courses>) coursesRepository
                .findAll(Sort.by("name").ascending().and(Sort.by("fees")));
        modelAndView.addObject("courses",courses);
        modelAndView.addObject("course",new Courses());
        return modelAndView;
    }

    @GetMapping("/viewStudents")
    public ModelAndView viewStudents(Model model,@RequestParam("id") int id,HttpSession session,
                                     @RequestParam(name = "error",required = false) Error error){
        String errorMessage=null;
        ModelAndView modelAndView = new ModelAndView("course_students");
        Optional<Courses> courses = coursesRepository.findById(id);
        modelAndView.addObject("courses",courses.get());
        modelAndView.addObject("person",new Person());
        session.setAttribute("courses",courses.get());
        if (error != null){
            errorMessage = "Sorry !!! , This student is not registered with us.";
            modelAndView.addObject("errorMessage",errorMessage);
        }
        return modelAndView;
    }
}
