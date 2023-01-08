package com.example.calendar.controller.calendar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    @GetMapping("/test")
    public String ControllerTest(){
        return "test성공";
    }
}
