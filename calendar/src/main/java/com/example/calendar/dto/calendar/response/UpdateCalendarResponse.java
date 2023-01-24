package com.example.calendar.dto.calendar.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCalendarResponse {
    private Long calendarId;
    private String title;
    private String description;
    private String color;
    private String category;
}
