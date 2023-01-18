package com.example.calendar.service.schedule;

import com.example.calendar.domain.schedule.Schedule;
import com.example.calendar.dto.schedule.request.CreateScheduleRequest;
import com.example.calendar.dto.schedule.request.UpdateScheduleRequest;
import com.example.calendar.dto.schedule.response.*;
import com.example.calendar.repository.schedule.ScheduleRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public SelectScheduleByIdResponse selectScheduleById(Long scheduleId) throws Exception {
        return ScheduleResponse.toSelectScheduleByIdResponse(scheduleRepository.findById(scheduleId).orElseThrow(Exception::new));
    }

    @Transactional
    public CreateScheduleResponse createSchedule(CreateScheduleRequest request){
        return ScheduleResponse.toCreateScheduleResponse(
                scheduleRepository.save(request.toSchedule())
        );
    }

    @Transactional
    public DeleteScheduleResponse deleteScheduleById(Long scheduleId)throws  Exception{
        Schedule schedule=scheduleRepository.findById(scheduleId).orElseThrow(Exception::new);
        scheduleRepository.delete(schedule);
        return ScheduleResponse.toDeleteScheduleResponse(schedule);
    }

    @Transactional
    public UpdateScheduleResponse updateSchedule(UpdateScheduleRequest request) throws Exception {
        Schedule schedule=scheduleRepository.findById(request.getScheduleId()).orElseThrow(Exception::new);
        schedule.updateSchedule(request);
        return ScheduleResponse.toUpdateScheduleResponse(schedule);
    }
}
