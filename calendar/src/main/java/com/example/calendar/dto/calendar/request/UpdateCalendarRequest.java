package com.example.calendar.dto.calendar.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCalendarRequest {
    @NotNull
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotBlank
    private String category;
    @NotBlank
    private String color;
}
