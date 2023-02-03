package com.example.calendar.service.schedule;

import com.example.calendar.domain.schedule.Schedule;
import com.example.calendar.dto.schedule.request.CreateScheduleRequest;
import com.example.calendar.dto.schedule.request.UpdateScheduleRequest;
import com.example.calendar.dto.schedule.response.*;
import com.example.calendar.global.error.ErrorCode;
import com.example.calendar.global.error.exception.CustomException;
import com.example.calendar.repository.schedule.ScheduleQueryDslRepository;
import com.example.calendar.repository.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.calendar.global.error.ErrorCode.CALENDAR_SCHEDULE_NOT_FOUND;
import static com.example.calendar.global.error.ErrorCode.SCHEDULE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleQueryDslRepository scheduleQueryDslRepository;

    @Transactional
    public SelectScheduleResponse selectScheduleById(Long scheduleId) throws Exception {
        return ScheduleResponse.toSelectScheduleResponse(scheduleRepository.findById(scheduleId).orElseThrow(()->new CustomException(SCHEDULE_NOT_FOUND)));
    }

    @Transactional
    public CreateScheduleResponse createSchedule(CreateScheduleRequest request){
        return ScheduleResponse.toCreateScheduleResponse(
                scheduleRepository.save(request.toSchedule())
        );
    }

    @Transactional
    public DeleteScheduleResponse deleteScheduleById(Long scheduleId)throws  Exception{
        Schedule schedule=scheduleRepository.findById(scheduleId).orElseThrow(()->new CustomException(SCHEDULE_NOT_FOUND));
        scheduleRepository.delete(schedule);
        return ScheduleResponse.toDeleteScheduleResponse(schedule);
    }

    @Transactional
    public UpdateScheduleResponse updateSchedule(UpdateScheduleRequest request) throws Exception {
        Schedule schedule=scheduleRepository.findById(request.getScheduleId()).orElseThrow(()->new CustomException(SCHEDULE_NOT_FOUND));
        schedule.updateSchedule(request);
        return ScheduleResponse.toUpdateScheduleResponse(schedule);
    }

    @Transactional
    public List<SelectScheduleResponse> selectScheduleList(Long calendarId,String startYmd,String endYmd) throws Exception{
        return Optional.ofNullable(scheduleQueryDslRepository.findScheduleList(calendarId,startYmd,endYmd))
                .orElseThrow(()->new CustomException(CALENDAR_SCHEDULE_NOT_FOUND)).stream().map(ScheduleResponse::toSelectScheduleResponse).collect(Collectors.toList());
    }
}
