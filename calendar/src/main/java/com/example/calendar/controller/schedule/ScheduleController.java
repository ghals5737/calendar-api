package com.example.calendar.controller.schedule;

import com.example.calendar.domain.schedule.Schedule;
import com.example.calendar.dto.schedule.request.CreateScheduleRequest;
import com.example.calendar.dto.schedule.request.UpdateScheduleRequest;
import com.example.calendar.dto.schedule.response.CreateScheduleResponse;
import com.example.calendar.dto.schedule.response.DeleteScheduleResponse;
import com.example.calendar.dto.schedule.response.SelectScheduleResponse;
import com.example.calendar.dto.schedule.response.UpdateScheduleResponse;
import com.example.calendar.service.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
@CrossOrigin(origins = "*")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/{scheduleId}")
    public SelectScheduleResponse selectScheduleById(@PathVariable Long scheduleId) throws Exception {
        return scheduleService.selectScheduleById(scheduleId);
    }

    @PostMapping
    public CreateScheduleResponse createSchedule(@RequestBody CreateScheduleRequest request){
        return scheduleService.createSchedule(request);
    }

    @DeleteMapping("/{scheduleId}")
    public DeleteScheduleResponse deleteSchedule(@PathVariable Long scheduleId) throws Exception {
        return scheduleService.deleteScheduleById(scheduleId);
    }

    @PutMapping
    public UpdateScheduleResponse updateSchedule(@RequestBody UpdateScheduleRequest request) throws Exception {
        return scheduleService.updateSchedule(request);
    }

    @GetMapping("/calendar/{calendarId}")
    public List<SelectScheduleResponse> selectScheduleList(@PathVariable Long calendarId,@RequestParam String startYmd,@RequestParam String endYmd) throws Exception {
        return scheduleService.selectScheduleList(calendarId, startYmd, endYmd);
    }
}
