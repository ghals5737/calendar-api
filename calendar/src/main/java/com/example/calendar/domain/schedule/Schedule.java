package com.example.calendar.domain.schedule;


import com.example.calendar.dto.schedule.request.UpdateScheduleRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long calendarId;
    private String title;
    private LocalDateTime startDt;
    private LocalDateTime endDt;
    private String description;
    private String color;

    public void updateSchedule(UpdateScheduleRequest request){
        this.title=request.getTitle();
        this.startDt=request.getStartDt();
        this.endDt=request.getEndDt();
        this.description=request.getDes();
        this.color=request.getColor();
    }
}
