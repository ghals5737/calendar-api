package com.example.calendar.service.calendar;

import com.example.calendar.dto.calendar.response.SelectCalendarByIdResponse;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@NoArgsConstructor
public class CalendarServiceTest {

    @Autowired
    private CalendarService calendarService;

    @Test
    @DisplayName("캘린더 API 조회 정상 동작 테스트")
    void selectCalendarByIdTest() {
        //given
        Long calendarId = 1L;
        SelectCalendarByIdResponse expect = SelectCalendarByIdResponse.builder()
                .calendarId(calendarId)
                .title("test title")
                .description("test des")
                .category("test category")
                .color("test color")
                .build();

        //when
        SelectCalendarByIdResponse response = calendarService.selectCalendarById(calendarId);

        //then
        assertThat(response.getTitle()).isEqualTo(expect.getTitle());
        assertThat(response.getCalendarId()).isEqualTo(expect.getCalendarId());
        assertThat(response.getCategory()).isEqualTo(expect.getCategory());
        assertThat(response.getDescription()).isEqualTo(expect.getDescription());
        assertThat(response.getColor()).isEqualTo(expect.getColor());

    }
}
