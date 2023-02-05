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
    public static CreateCalendarResponse toCreateCalendarResponse(Calendar calendar) {
        return CreateCalendarResponse.builder()
                .calendarId(calendar.getId())
                .title(calendar.getTitle())
                .description(calendar.getDescription())
                .category(calendar.getCategory())
                .color(calendar.getColor())
                .build();
    }

    public static DeleteCalendarResponse toDeleteCalendarResponse(Calendar calendar) {
        return DeleteCalendarResponse.builder()
                .calendarId(calendar.getId())
                .build();
    }

    public static UpdateCalendarResponse toUpdateCalendarResponse(Calendar calendar) {
        return UpdateCalendarResponse.builder()
                .calendarId(calendar.getId())
                .category(calendar.getCategory())
                .color(calendar.getColor())
                .description(calendar.getDescription())
                .build();
    }
}
