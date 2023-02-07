package com.example.calendar.repository.calendar;

import com.example.calendar.domain.calendar.Calendar;
import com.example.calendar.domain.mapping.UserCalendarMpng;
import com.example.calendar.domain.user.User;
import com.example.calendar.dto.calendar.condition.CalendarSearchByUserIdCondition;
import com.example.calendar.repository.mapping.UserCalendarMpngRepository;
import com.example.calendar.repository.user.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
@Slf4j
@SpringBootTest
@NoArgsConstructor
class CalendarRepositoryImplTest {

    @Autowired
    private CalendarQueryDslRepository calendarQueryDslRepository;

    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCalendarMpngRepository userCalendarMpngRepository;

    Calendar calendar;
    User user;
    UserCalendarMpng userCalendarMpng;

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

        userCalendarMpng = userCalendarMpngRepository.save(UserCalendarMpng.builder()
                .calendarId(calendar.getId())
                .userId(user.getId())
                .build());
    }
    @AfterEach
    public void clear() {
        calendarRepository.deleteAll();
        userCalendarMpngRepository.deleteAll();
    }
    @Test
    void searchTest() {
        CalendarSearchByUserIdCondition condition = CalendarSearchByUserIdCondition
                .builder()
                .userId(user.getId())
                .build();
//       List<Tuple> search = calendarRepositoryCustom.searchByUserId(condition);
//       Tuple tuple = search.get(0);
//       assertThat(tuple.get(QCalendar.calendar.category)).isEqualTo(calendar.getCategory());
    }
}