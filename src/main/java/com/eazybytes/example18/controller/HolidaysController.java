package com.eazybytes.example18.controller;

import com.eazybytes.example18.Model.Holiday;
import com.eazybytes.example18.Repository.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class HolidaysController {

    @Autowired
    private HolidaysRepository holidaysRepository;

//    @GetMapping("/holidays")
//    public String displayHolidays(@RequestParam(required = false) boolean festival,
//                                  @RequestParam(required = false) boolean national, Model model){
//
//        model.addAttribute("festival",festival);
//        model.addAttribute("national",national);
//
//        List<Holiday> holidays= Arrays.asList( new Holiday("25th", "Christmas", Holiday.HolidayType.FESTIVAL),
//                       new Holiday("26th", "Republic Day", Holiday.HolidayType.NATIONAL),
//                       new Holiday("12th", "Holi", Holiday.HolidayType.FESTIVAL),
//                       new Holiday("14th", "Raksha Bandhan", Holiday.HolidayType.FESTIVAL),
//                       new Holiday("15th", "Independence Day", Holiday.HolidayType.NATIONAL),
//                       new Holiday("O2th", "Gandhi Jayanti", Holiday.HolidayType.NATIONAL),
//                       new Holiday("15th", "Ganesh Chaturthi", Holiday.HolidayType.FESTIVAL));
//
//        Holiday.HolidayType[] types= Holiday.HolidayType.values();
//        for (Holiday.HolidayType type:types) {
//            model.addAttribute(type.toString(), (holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
//        }
//        return "holidays.html";
//    }

    @GetMapping("/holidays/{display}")
    public String displayHolidays(@PathVariable String display, Model model) {
        if(null != display && display.equals("all")){
            model.addAttribute("festival",true);
            model.addAttribute("federal",true);
        }else if(null != display && display.equals("federal")){
            model.addAttribute("federal",true);
        }else if(null != display && display.equals("festival")){
            model.addAttribute("festival",true);
        }
        Holiday.Type[] types = Holiday.Type.values();
//        List<Holiday> holiDay = holidaysRepository.findAllHolidays();
        Iterable<Holiday> holiDay= holidaysRepository.findAll();

//        CONVERT STREAM OF ITERABLE OBJECT INTO LIST
//=========================================================================================================
        List<Holiday> holidays = StreamSupport.stream(holiDay.spliterator(), false).collect(Collectors.toList());
//=========================================================================================================
        for (Holiday.Type type : types) {
            model.addAttribute(type.toString(),
                    (holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }
        return "holidays.html";
    }
}