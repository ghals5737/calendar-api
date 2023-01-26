package com.example.calendar.service.calendar;

import com.example.calendar.domain.calendar.Calendar;
import com.example.calendar.dto.calendar.request.CreateCalendarRequest;
import com.example.calendar.dto.calendar.request.UpdateCalendarRequest;
import com.example.calendar.dto.calendar.response.CreateCalendarResponse;
import com.example.calendar.dto.calendar.response.SelectCalendarByIdResponse;
import com.example.calendar.dto.calendar.response.UpdateCalendarResponse;
import com.example.calendar.repository.calendar.CalendarRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Slf4j
@SpringBootTest
@NoArgsConstructor
public class CalendarServiceTest {

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private CalendarRepository calendarRepository;

    @AfterEach
    public void clear() {
        calendarRepository.deleteAll();
    }

    @Test
    @DisplayName("캘린더 API 조회 정상 동작 테스트")
    void selectCalendarByIdTest() throws Exception {
        //given
        Calendar expect = calendarRepository.save(
                Calendar.builder()
                        .color("yellow")
                        .title("trip calendar")
                        .category("trip")
                        .description("for planning trips")
                        .build());

        //when
        SelectCalendarByIdResponse response = calendarService.selectCalendarById(expect.getId());

        //then
        assertThat(response.getTitle()).isEqualTo(expect.getTitle());
        assertThat(response.getCalendarId()).isEqualTo(expect.getId());
        assertThat(response.getCategory()).isEqualTo(expect.getCategory());
        assertThat(response.getDescription()).isEqualTo(expect.getDescription());
        assertThat(response.getColor()).isEqualTo(expect.getColor());

    }

    @Test
    @DisplayName("캘린더 생성 API 정상 동작 테스트")
    void createCalendarTest() {
        // given
        CreateCalendarRequest request = CreateCalendarRequest.builder()
                .title("test title")
                .description("test des")
                .category("test category")
                .color("test color")
                .build();

        // when
        CreateCalendarResponse calendar = calendarService.createCalendar(request);

        // then
        assertThat(request.getTitle()).isEqualTo(calendar.getTitle());
        assertThat(request.getDescription()).isEqualTo(calendar.getDescription());
        assertThat(request.getCategory()).isEqualTo(calendar.getCategory());
        assertThat(request.getColor()).isEqualTo(calendar.getColor());
    }

    @Test
    @DisplayName("캘린더 삭제 API 정상 동작 테스트")
    void deleteCalendarTest() {

        // given
        Calendar calendar = Calendar.builder()
                .title("test title")
                .description("test des")
                .category("test category")
                .color("test color")
                .build();

        Calendar save = calendarRepository.save(calendar);

        // when
        calendarService.deleteCalendarById(save.getId());

        // then
        Optional<Calendar> byId = calendarRepository.findById(save.getId());
        assertThat(byId.isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("캘린더 수정 API 정상 동작 테스트")
    void updateCalendarTest() {

        // given
        Calendar calendar = Calendar.builder()
                .title("test title")
                .description("test des")
                .category("test category")
                .color("test color")
                .build();

        Calendar save = calendarRepository.save(calendar);

        // when
        UpdateCalendarRequest request = UpdateCalendarRequest.builder()
                .id(save.getId())
                .title("update title")
                .description("update des")
                .category("update category")
                .color("update color")
                .build();
        UpdateCalendarResponse updated = calendarService.updateCalendar(request);

        // then
        List<Calendar> results = calendarRepository.findAll();
        assertThat(results).hasSize(1);
        AssertionsForClassTypes.assertThat(results.get(0).getId()).isEqualTo(request.getId());
        AssertionsForClassTypes.assertThat(results.get(0).getCategory()).isEqualTo(request.getCategory());
        AssertionsForClassTypes.assertThat(results.get(0).getTitle()).isEqualTo(request.getTitle());
        AssertionsForClassTypes.assertThat(results.get(0).getDescription()).isEqualTo(request.getDescription());
        AssertionsForClassTypes.assertThat(results.get(0).getColor()).isEqualTo(request.getColor());
    }
}
