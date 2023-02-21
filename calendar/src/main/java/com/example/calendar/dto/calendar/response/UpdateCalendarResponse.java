package com.example.calendar.dto.calendar.response;

import com.example.calendar.domain.category.type.CategoryType;
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
    private CategoryType category;
}
