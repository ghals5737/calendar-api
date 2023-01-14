package com.example.calendar.dto.schedule.request;

import com.example.calendar.domain.schedule.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateScheduleRequest {
    private Long calendarId;
    private String title;
    private LocalDateTime startDt;
    private LocalDateTime endDt;
    private String des;
    private String color;

    public Schedule toSchedule(){
        return Schedule.builder()
                .calendarId(this.calendarId)
                .title(this.title)
                .startDt(this.startDt)
                .endDt(this.endDt)
                .description(des)
                .color(color)
                .build();
    }
}
