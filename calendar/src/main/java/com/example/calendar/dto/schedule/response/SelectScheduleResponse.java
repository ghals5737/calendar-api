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
public class SelectScheduleResponse {
    private Long scheduleId;
    private Long calendarId;
    private String title;
    private String startYmd;
    private String endYmd;
    private LocalDateTime startDt;
    private LocalDateTime endDt;
    private String des;
    private String color;
}
