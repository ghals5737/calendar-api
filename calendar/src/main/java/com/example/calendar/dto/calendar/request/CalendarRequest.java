package com.example.calendar.dto.calendar.request;

import com.example.calendar.domain.calendar.Calendar;
import com.example.calendar.dto.calendar.response.SelectCalendarByIdResponse;

public class CalendarRequest {
    public static Calendar toCalendarByCreateRequest(CreateCalendarRequest calendar) {
        return Calendar.builder()
                .title(calendar.getTitle())
                .description(calendar.getDescription())
                .category(calendar.getCategory())
                .color(calendar.getColor())
                .build();
    }
}
