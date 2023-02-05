package com.example.calendar.dto.calendar.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectCalendarByIdResponse {
    private Long calendarId;
    private String title;
    private String description;
    private String category;
    private String color;
}


