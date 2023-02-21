package com.example.calendar.dto.calendar.response;

import com.example.calendar.domain.category.type.CategoryType;
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
    private CategoryType category;
    private String color;
}


