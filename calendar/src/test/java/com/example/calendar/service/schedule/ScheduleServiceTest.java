package com.example.calendar.service.schedule;

import com.example.calendar.domain.schedule.Schedule;
import com.example.calendar.dto.schedule.request.CreateScheduleRequest;
import com.example.calendar.dto.schedule.response.CreateScheduleResponse;
import com.example.calendar.dto.schedule.response.SelectScheduleByIdResponse;
import com.example.calendar.repository.schedule.ScheduleRepository;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@NoArgsConstructor
public class ScheduleServiceTest {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @AfterEach
    public void clear(){
        scheduleRepository.deleteAll();
    }

    @Test
    @DisplayName("schedule_id로 schedule 조회가 정상 작동한다.")
    void selectScheduleByIdTest() throws Exception {
        //given
        Schedule expect=scheduleRepository.save(Schedule.builder()
                .calendarId(1L)
                .title("test")
                .startDt(LocalDateTime.now())
                .endDt(LocalDateTime.now())
                .description("test")
                .color("test")
                .build());
        //when
        SelectScheduleByIdResponse result=scheduleService.selectScheduleById(expect.getId());
        //then
        assertThat(result.getTitle()).isEqualTo(expect.getTitle());
    }

    @Test
    @DisplayName("schedule생성이 정상 작동한다.")
    void createScheduleTest() throws Exception {
        //given
        CreateScheduleRequest request= CreateScheduleRequest.builder()
                .calendarId(1L)
                .title("test")
                .startDt(LocalDateTime.now())
                .endDt(LocalDateTime.now())
                .des("test des")
                .color("test color")
                .build();
        //when
        CreateScheduleResponse result=scheduleService.createSchedule(request);

        //then
        List<Schedule> results=scheduleRepository.findAll();
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getId()).isEqualTo(result.getScheduleId());
        assertThat(results.get(0).getCalendarId()).isEqualTo(request.getCalendarId());
        assertThat(results.get(0).getTitle()).isEqualTo(request.getTitle());
        assertThat(results.get(0).getDescription()).isEqualTo(request.getDes());
        assertThat(results.get(0).getColor()).isEqualTo(request.getColor());
    }
}
