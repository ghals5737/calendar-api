package com.example.calendar.dto.calendar.request;

import com.example.calendar.domain.calendar.Calendar;
import com.example.calendar.domain.category.type.CategoryType;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCalendarRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private CategoryType category;
    @NotBlank
    private String color;
    @NotNull
    private Long userId;


    public Calendar toCalendar() {
        return Calendar.builder()
                .category(this.getCategory())
                .color(this.getColor())
                .description(this.getDescription())
                .title(this.getTitle())
                .build();
    }
}
