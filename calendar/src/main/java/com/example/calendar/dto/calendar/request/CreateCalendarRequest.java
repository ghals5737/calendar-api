package com.example.calendar.dto.calendar.request;

import com.example.calendar.domain.calendar.Calendar;
import com.example.calendar.domain.category.type.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCalendarRequest {

    @NotBlank(message = "제목에 공백이 포함될 수 없습니다.")
    private String title;
    @NotBlank(message = "설명이 공백이 포함될 수 없습니다.")
    private String description;
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
