package com.example.calendar.dto.schedule.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateScheduleRequest {
    private Long scheduleId;
    private String title;
    private LocalDateTime startDt;
    private LocalDateTime endDt;
    private String startYmd;
    private String endYmd;
    private String des;
    private String color;
}
