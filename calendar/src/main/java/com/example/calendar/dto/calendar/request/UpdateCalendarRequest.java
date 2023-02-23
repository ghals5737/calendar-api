package com.example.calendar.dto.calendar.request;

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
public class UpdateCalendarRequest {
    @NotNull
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private CategoryType category;
    @NotEmpty
    private String color;
}
