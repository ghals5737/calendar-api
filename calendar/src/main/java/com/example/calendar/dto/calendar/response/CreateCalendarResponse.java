package com.example.calendar.dto.calendar.response;

import com.example.calendar.domain.category.type.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCalendarResponse {
    private Long calendarId;
    private String title;
    private String description;
    private CategoryType category;
    private String color;
}
