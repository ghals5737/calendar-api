package com.example.calendar.domain.calendar;

import com.example.calendar.dto.calendar.request.UpdateCalendarRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Calendar {
    @Id
    @Column(name = "calendar_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String color;
    private String category;

    public void updateCalendar(UpdateCalendarRequest request) {
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.color = request.getColor();
        this.category = request.getCategory();
    }
}
