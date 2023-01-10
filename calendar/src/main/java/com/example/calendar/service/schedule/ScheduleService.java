package com.example.calendar.service.schedule;

import com.example.calendar.dto.schedule.response.ScheduleResponse;
import com.example.calendar.dto.schedule.response.SelectScheduleByIdResponse;
import com.example.calendar.repository.schedule.ScheduleRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public SelectScheduleByIdResponse selectScheduleById(Long scheduleId) throws Exception {
        return ScheduleResponse.toSelectScheduleByIdResponse(scheduleRepository.findById(scheduleId).orElseThrow(Exception::new));
    }
}
