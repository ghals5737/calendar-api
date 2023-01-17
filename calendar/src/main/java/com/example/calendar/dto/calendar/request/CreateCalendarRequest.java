package com.example.calendar.dto.calendar.request;

import com.example.calendar.domain.calendar.Calendar;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCalendarRequest {
    private String title;
    private String description;
    private String category;
    private String color;


    public Calendar toCalendar() {
        return Calendar.builder()
                .category(this.getCategory())
                .color(this.getColor())
                .description(this.getDescription())
                .title(this.getTitle())
                .build();
    }
}
