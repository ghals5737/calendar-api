package com.example.calendar.controller.schedule;

import com.example.calendar.dto.schedule.request.CreateScheduleRequest;
import com.example.calendar.dto.schedule.response.CreateScheduleResponse;
import com.example.calendar.dto.schedule.response.SelectScheduleByIdResponse;
import com.example.calendar.service.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/{scheduleId}")
    public SelectScheduleByIdResponse selectScheduleById(@PathVariable Long scheduleId) throws Exception {
        return scheduleService.selectScheduleById(scheduleId);
    }

    @PostMapping
    public CreateScheduleResponse createSchedule(@RequestBody CreateScheduleRequest request){
        return scheduleService.createSchedule(request);
    }
}