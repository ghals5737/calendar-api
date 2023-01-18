package com.example.calendar.dto.schedule.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateScheduleResponse {
    private Long scheduleId;
    private Long calendarId;
    private String title;
    private LocalDateTime startDt;
    private LocalDateTime endDt;
    private String des;
    private String color;
}

