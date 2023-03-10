package com.example.calendar.controller.calendar;

import com.example.calendar.dto.calendar.request.CreateCalendarRequest;
import com.example.calendar.dto.calendar.request.UpdateCalendarRequest;
import com.example.calendar.dto.calendar.response.CreateCalendarResponse;
import com.example.calendar.dto.calendar.response.DeleteCalendarResponse;
import com.example.calendar.dto.calendar.response.SelectCalendarByIdResponse;
import com.example.calendar.dto.calendar.response.UpdateCalendarResponse;
import com.example.calendar.dto.schedule.request.UpdateScheduleRequest;
import com.example.calendar.dto.schedule.response.UpdateScheduleResponse;
import com.example.calendar.service.calendar.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import java.util.List;

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
    public void deleteCalendarById(@PathVariable Long id,@RequestParam Long userId) {
        calendarService.deleteCalendarByIdAndUserId(id,userId);
    }

    @PutMapping
    public UpdateCalendarResponse updateCalendar(@RequestBody @Valid UpdateCalendarRequest request) throws Exception {
        return calendarService.updateCalendar(request);
    }

    @GetMapping("/users/{id}")
    public List<SelectCalendarByIdResponse> selectCalendarByUserId(@PathVariable Long id) throws Exception {
        return calendarService.searchByUserId(id);
    }
}
