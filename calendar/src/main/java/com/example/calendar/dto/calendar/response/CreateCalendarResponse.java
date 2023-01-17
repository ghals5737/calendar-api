package com.example.calendar.dto.calendar.response;

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
    private String title;
    private String description;
    private String category;
    private String color;
}