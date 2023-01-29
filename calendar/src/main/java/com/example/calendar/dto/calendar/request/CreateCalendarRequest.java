package com.example.calendar.dto.calendar.request;

import com.example.calendar.domain.calendar.Calendar;
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
    private String category;
    @NotBlank
    private String color;
    @NotBlank
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
