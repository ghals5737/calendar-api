package com.example.calendar.service.calendar;

import com.example.calendar.domain.calendar.Calendar;
import com.example.calendar.domain.user.User;
import com.example.calendar.domain.user.UserCalendar;
import com.example.calendar.dto.calendar.request.CreateCalendarRequest;
import com.example.calendar.dto.calendar.request.UpdateCalendarRequest;
import com.example.calendar.dto.calendar.response.CreateCalendarResponse;
import com.example.calendar.dto.calendar.response.SelectCalendarByIdResponse;
import com.example.calendar.dto.calendar.response.UpdateCalendarResponse;
import com.example.calendar.repository.calendar.CalendarRepository;
import com.example.calendar.repository.user.UserCalendarRepository;
import com.example.calendar.repository.user.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Slf4j
@SpringBootTest
@NoArgsConstructor
public class CalendarServiceTest {

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCalendarRepository userCalendarRepository;

    @AfterEach
    public void clear() {
        calendarRepository.deleteAll();
        userCalendarRepository.deleteAll();
    }

    Calendar calendar;
    User user;
    UserCalendar userCalendar;

    @BeforeEach
    public void create() {
        calendar = calendarRepository.save(
                Calendar.builder()
                        .color("yellow")
                        .title("trip calendar")
                        .category("trip")
                        .description("for planning trips")
                        .build());
        user = userRepository.save(User.builder()
                .nickname("star")
                .password("pw")
                .email("abc@gmail.com")
                .birthday(LocalDate.of(2023, 1, 26))
                .build());

        userCalendar = UserCalendar.builder()
                .calendarId(calendar.getId())
                .userId(user.getId())
                .build();

        userCalendarRepository.save(userCalendar);
    }

    @Test
    @DisplayName("캘린더 API 조회 정상 동작 테스트")
    void selectCalendarByIdTest() throws Exception {
        //given

        //when
        SelectCalendarByIdResponse response = calendarService.selectCalendarById(calendar.getId());

        //then
        assertThat(response.getTitle()).isEqualTo(calendar.getTitle());
        assertThat(response.getCalendarId()).isEqualTo(calendar.getId());
        assertThat(response.getCategory()).isEqualTo(calendar.getCategory());
        assertThat(response.getDescription()).isEqualTo(calendar.getDescription());
        assertThat(response.getColor()).isEqualTo(calendar.getColor());
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

        // when
        calendarService.deleteCalendarById(calendar.getId());

        // then
        Optional<Calendar> byId = calendarRepository.findById(calendar.getId());
        assertThat(byId.isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("캘린더 수정 API 정상 동작 테스트")
    void updateCalendarTest() {
        // given

        // when
        UpdateCalendarRequest request = UpdateCalendarRequest.builder()
                .id(calendar.getId())
                .title("update title")
                .description("update des")
                .category("update category")
                .color("update color")
                .build();
        UpdateCalendarResponse updated = calendarService.updateCalendar(request);

        // then
        List<Calendar> results = calendarRepository.findAll();
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getId()).isEqualTo(request.getId());
        assertThat(results.get(0).getCategory()).isEqualTo(request.getCategory());
        assertThat(results.get(0).getTitle()).isEqualTo(request.getTitle());
        assertThat(results.get(0).getDescription()).isEqualTo(request.getDescription());
        assertThat(results.get(0).getColor()).isEqualTo(request.getColor());
    }

    @Test
    @DisplayName("사용자의 캘린더 조회 API 정상 동작 테스트")
    void selectCalendarsByUserIdTest() throws Exception {
        //given

        //when
        List<UserCalendar> userCalendars = userCalendarRepository.findByUserId(user.getId());
        List<SelectCalendarByIdResponse> calendars = new ArrayList<>();
        for (UserCalendar calendar : userCalendars) {
            SelectCalendarByIdResponse selectCalendarByIdResponse = calendarService.selectCalendarById(calendar.getCalendarId());
            calendars.add(selectCalendarByIdResponse);
        }

        //then
        assertThat(calendars).hasSize(1);
        assertThat(calendars.get(0).getCalendarId()).isEqualTo(calendar.getId());
        assertThat(calendars.get(0).getTitle()).isEqualTo(calendar.getTitle());
        assertThat(calendars.get(0).getCategory()).isEqualTo(calendar.getCategory());
        assertThat(calendars.get(0).getDescription()).isEqualTo(calendar.getDescription());
    }

    @Test
    @DisplayName("사용자의 캘린더 생성 API 정상 동작 테스트")
    void createUserCalendarTest() {
        // given
        CreateCalendarRequest request = CreateCalendarRequest.builder()
                .title("test title")
                .description("test des")
                .category("test category")
                .color("test color")
                .userId(user.getId())
                .build();
        // when
        CreateCalendarResponse calendar = calendarService.createCalendar(request);

        // then
        assertThat(request.getCategory()).isEqualTo(calendar.getCategory());
        assertThat(request.getColor()).isEqualTo(calendar.getColor());
        assertThat(request.getDescription()).isEqualTo(calendar.getDescription());
        assertThat(request.getTitle()).isEqualTo(calendar.getTitle());
    }

    @Test
    @DisplayName("사용자의 캘린더 삭제 API 정상 동작 테스트")
    void deleteUserCalendarTest() {
        // given

        // when
        calendarService.deleteCalendarById(calendar.getId());

        // then
        Optional<Calendar> byId = calendarRepository.findById(calendar.getId());
        assertThat(byId.isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("사용자의 캘린더 수정 API 정상 동작 테스트")
    void updateUserCalendarTest() {

        // 사용자와 캘린더가 1:1이면 캘린더 정보만 수정하면 된다.
        // 사용자쪽은 수정, 검증할 것이 없다.

        // given

        // when
        UpdateCalendarRequest request = UpdateCalendarRequest.builder()
                .id(calendar.getId())
                .title("update title")
                .description("update des")
                .category("update category")
                .color("update color")
                .build();

        calendarService.updateCalendar(request);

        // then
        List<Calendar> results = calendarRepository.findAll();
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getId()).isEqualTo(request.getId());
        assertThat(results.get(0).getCategory()).isEqualTo(request.getCategory());
        assertThat(results.get(0).getTitle()).isEqualTo(request.getTitle());
        assertThat(results.get(0).getDescription()).isEqualTo(request.getDescription());
        assertThat(results.get(0).getColor()).isEqualTo(request.getColor());
    }
}
