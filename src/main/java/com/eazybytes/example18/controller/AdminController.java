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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private eazyClassRepository eazyClassRepository;

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

    @PostMapping("/addNewClass")
    public ModelAndView addNewClass(Model model,@ModelAttribute("eazyClass") EazyClass eazyClass){
        eazyClassRepository.save(eazyClass);
        return new ModelAndView("redirect:/admin/displayClasses");
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(Model model,@RequestParam int id){
        Optional<EazyClass> eazyClass = eazyClassRepository.findById(id);
        for (Person person:eazyClass.get().getPersons()) {
            person.setEazyClass(null);
            personRepo.save(person);
        }
        eazyClassRepository.deleteById(id);
        return new ModelAndView("redirect:/admin/displayClasses");
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

    @PostMapping("/addStudent")
    public ModelAndView addStudent(Model model,@ModelAttribute("person") Person person,HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        EazyClass eazyClass = (EazyClass) session.getAttribute("eazyClass");
        Person personEntity = personRepo.readByEmail(person.getEmail());
        if (personEntity==null || !(personEntity.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/displayStudents?classId="
                    +eazyClass.getClassId()+"&error=true");
            return modelAndView;
        }
//      CONNECT EASY CLASS TO PERSON
        personEntity.setEazyClass(eazyClass);
//      SAVING AFTER ADDED CONNECTION
        personRepo.save(personEntity);
//      CONNECT PERSON TO EASY CLASS
        eazyClass.getPersons().add(personEntity);
        eazyClassRepository.save(eazyClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId="+eazyClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(Model model,@RequestParam int personId,HttpSession session){
        EazyClass eazyClass = (EazyClass) session.getAttribute("eazyClass");
        Optional<Person> person = personRepo.findById(personId);
        person.get().setEazyClass(null);
        eazyClass.getPersons().remove(person.get());
        EazyClass eazyClassSaved = eazyClassRepository.save(eazyClass);
        session.setAttribute("eazyClass",eazyClassSaved);
        return new ModelAndView("redirect:/admin/displayStudents?classId="+eazyClass.getClassId());
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

//    ADD NEW COURSE IN EAZY SCHOOL
    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(Model model,@ModelAttribute("course") Courses course){
        coursesRepository.save(course);
        return new ModelAndView("redirect:/admin/displayCourses");
    }

//    VIEW STUDENTS WHO ARE REGISTER WITH THOSE EXTRA COURSES
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

    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(Model model,@ModelAttribute("person") Person person,HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        Courses courses= (Courses) session.getAttribute("courses");
        Person personEntity = personRepo.readByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId()+"&error=true");
            return modelAndView;
        }
        personEntity.getCourses().add(courses);
        courses.getPersons().add(personEntity);
        personRepo.save(personEntity);
        session.setAttribute("courses",courses);
        modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId());
        return modelAndView;
    }

    @GetMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(Model model,@RequestParam("personId") int personId,HttpSession session){
        Courses courses = (Courses) session.getAttribute("courses");
        Optional<Person> personEntity = personRepo.findById(personId);
        personEntity.get().setCourses(null);
        courses.getPersons().remove(personEntity.get());
        coursesRepository.save(courses);
        return new ModelAndView("redirect:/admin/viewStudents?id="+courses.getCourseId());
    }
}

