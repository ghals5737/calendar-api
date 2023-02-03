package com.example.calendar.dto.calendar.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
public class SelectCalendarByIdResponse {
    private Long calendarId;
    private String title;
    private String description;
    private String category;
    private String color;

    @QueryProjection
    public SelectCalendarByIdResponse(Long calendarId,
                                      String title,
                                      String description,
                                      String category,
                                      String color
    ) {
        this.calendarId = calendarId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.color = color;
    }
}


