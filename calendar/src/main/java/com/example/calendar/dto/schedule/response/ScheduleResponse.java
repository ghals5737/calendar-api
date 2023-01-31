package com.example.calendar.dto.schedule.response;


import com.example.calendar.domain.schedule.Schedule;

public class ScheduleResponse {
    public static SelectScheduleResponse toSelectScheduleResponse(Schedule schedule) {
        return SelectScheduleResponse.builder()
                .scheduleId(schedule.getId())
                .calendarId(schedule.getCalendarId())
                .title(schedule.getTitle())
                .des(schedule.getDescription())
                .startYmd(schedule.getStartYmd())
                .endYmd(schedule.getEndYmd())
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

    public static UpdateScheduleResponse toUpdateScheduleResponse(Schedule schedule) {
        return UpdateScheduleResponse.builder()
                .scheduleId(schedule.getId())
                .calendarId(schedule.getCalendarId())
                .title(schedule.getTitle())
                .des(schedule.getDescription())
                .startDt(schedule.getStartDt())
                .endDt(schedule.getEndDt())
                .color(schedule.getColor())
                .build();
    }
}
