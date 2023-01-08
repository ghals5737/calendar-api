package com.example.calendar.dto.calendar.response;

import com.example.calendar.domain.calendar.Calendar;

public class CalendarResponse {
    public static SelectCalendarByIdResponse toSelectCalendarByIdResponse(Calendar calendar) {
        return SelectCalendarByIdResponse.builder()
                .calendarId(calendar.getId())
                .title(calendar.getTitle())
                .description(calendar.getDescription())
                .category(calendar.getCategory())
                .color(calendar.getColor())
                .build();
    }
}
