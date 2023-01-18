package com.example.calendar.controller.calendar;

import com.example.calendar.dto.calendar.request.CreateCalendarRequest;
import com.example.calendar.dto.calendar.response.CreateCalendarResponse;
import com.example.calendar.dto.calendar.response.DeleteCalendarResponse;
import com.example.calendar.dto.calendar.response.SelectCalendarByIdResponse;
import com.example.calendar.service.calendar.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Digits;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @PostMapping
    public CreateCalendarResponse createCalendar(@RequestBody @Valid CreateCalendarRequest request) {
        return calendarService.createCalendar(request);
    }

    @GetMapping("{id}")
    public SelectCalendarByIdResponse selectCalendarById(@PathVariable Long id) throws Exception {
        return calendarService.selectCalendarById(id);
    }

    @DeleteMapping("{id}")
    public DeleteCalendarResponse deleteCalendarById(@PathVariable Long id) {
        return calendarService.deleteCalendarById(id);
    }


}
