package com.example.calendar.service.schedule;

import com.example.calendar.domain.schedule.Schedule;
import com.example.calendar.dto.schedule.request.CreateScheduleRequest;
import com.example.calendar.dto.schedule.request.UpdateScheduleRequest;
import com.example.calendar.dto.schedule.response.CreateScheduleResponse;
import com.example.calendar.dto.schedule.response.DeleteScheduleResponse;
import com.example.calendar.dto.schedule.response.SelectScheduleResponse;
import com.example.calendar.dto.schedule.response.UpdateScheduleResponse;
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
        SelectScheduleResponse result=scheduleService.selectScheduleById(expect.getId());
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

    @Test
    @DisplayName("scheduleID로 삭제가 정상 작동한다.")
    void deleteScheduleTest() throws Exception {
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
        DeleteScheduleResponse result=scheduleService.deleteScheduleById(expect.getId());

        //then
        List<Schedule> results=scheduleRepository.findAll();
        assertThat(results).isEmpty();
        assertThat(expect.getId()).isEqualTo(result.getScheduleId());
    }

    @Test
    @DisplayName("schedule수정이 정상 작동한다.")
    void updateScheduleTest() throws Exception {
        //given
        Schedule expect=scheduleRepository.save(Schedule.builder()
                .calendarId(1L)
                .title("test")
                .startDt(LocalDateTime.now())
                .endDt(LocalDateTime.now())
                .description("test")
                .color("test")
                .build());
        UpdateScheduleRequest request= UpdateScheduleRequest.builder()
                .scheduleId(expect.getId())
                .title("update test")
                .startDt(LocalDateTime.now())
                .endDt(LocalDateTime.now())
                .des("update test des")
                .color("update test color")
                .build();
        //when
        UpdateScheduleResponse result=scheduleService.updateSchedule(request);

        //then
        List<Schedule> results=scheduleRepository.findAll();
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getId()).isEqualTo(result.getScheduleId());
        assertThat(results.get(0).getCalendarId()).isEqualTo(result.getCalendarId());
        assertThat(results.get(0).getTitle()).isEqualTo(result.getTitle());
        assertThat(results.get(0).getDescription()).isEqualTo(result.getDes());
        assertThat(results.get(0).getColor()).isEqualTo(result.getColor());
    }

    @Test
    @DisplayName("조회하고싶은 월의 시작년원일과 끝년월일을 받아서 scheduleList를 조회하는 서비스가 정상 작동한다.")
    void selectScheduleListTest() throws Exception {
        //given
        //2023년 01 월 기준
        Long calendarId=1L;
        String startYmd="20230101";
        String endYmd="20230204";

        //시작날짜 끝날짜 모두 조회하고 싶은 월안에 있음
        Schedule expect1=scheduleRepository.save(Schedule.builder()
                .calendarId(1L)
                .title("test1")
                .startDt(LocalDateTime.now())
                .endDt(LocalDateTime.now())
                .startYmd("20230102")
                .endYmd("20230112")
                .description("test1")
                .color("test1")
                .build());

        //시작날짜는 전월 끝날짜는 포함
        Schedule expect2=scheduleRepository.save(Schedule.builder()
                .calendarId(1L)
                .title("test2")
                .startDt(LocalDateTime.now())
                .endDt(LocalDateTime.now())
                .startYmd("20221202")
                .endYmd("20230106")
                .description("test2")
                .color("test2")
                .build());

        //시작날짜는 포함 끝날짜는 다음월
        Schedule expect3=scheduleRepository.save(Schedule.builder()
                .calendarId(1L)
                .title("test3")
                .startDt(LocalDateTime.now())
                .endDt(LocalDateTime.now())
                .startYmd("20230102")
                .endYmd("20230206")
                .description("test3")
                .color("test3")
                .build());

        //시작,끝날짜 모두 포함 X
        Schedule expect4=scheduleRepository.save(Schedule.builder()
                .calendarId(1L)
                .title("test4")
                .startDt(LocalDateTime.now())
                .endDt(LocalDateTime.now())
                .startYmd("20221202")
                .endYmd("20230206")
                .description("test4")
                .color("test4")
                .build());


        //when
        List<SelectScheduleResponse> results=scheduleService.selectScheduleList(calendarId,startYmd,endYmd);

        //then
        assertThat(results).hasSize(4);
        assertThat(results)
                .extracting("title")
                .containsExactlyInAnyOrder(expect1.getTitle(),expect2.getTitle(),expect3.getTitle(),expect4.getTitle());
    }
}
