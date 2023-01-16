package com.example.calendar.dto.schedule.response;


import com.example.calendar.domain.schedule.Schedule;

public class ScheduleResponse {
    public static SelectScheduleByIdResponse toSelectScheduleByIdResponse(Schedule schedule) {
        return SelectScheduleByIdResponse.builder()
                .scheduleId(schedule.getId())
                .calendarId(schedule.getCalendarId())
                .title(schedule.getTitle())
                .des(schedule.getDescription())
                .startDt(schedule.getStartDt())
                .endDt(schedule.getEndDt())
                .color(schedule.getColor())
                .build();
    }

    public static CreateScheduleResponse toCreateScheduleResponse(Schedule schedule){
        return CreateScheduleResponse.builder()
                .scheduleId(schedule.getId())
                .build();
    }

    public static DeleteScheduleResponse toDeleteScheduleResponse(Schedule schedule){
        return DeleteScheduleResponse.builder()
                .scheduleId(schedule.getId())
                .build();
    }
}
