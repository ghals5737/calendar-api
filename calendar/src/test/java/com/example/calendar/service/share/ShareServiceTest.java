package com.example.calendar.service.share;

import com.example.calendar.domain.calendar.Calendar;
import com.example.calendar.domain.category.type.CategoryType;
import com.example.calendar.domain.mapping.UserCalendarMpng;
import com.example.calendar.domain.user.User;
import com.example.calendar.domain.user.type.SnsType;
import com.example.calendar.dto.share.request.ShareCalendarRequest;
import com.example.calendar.dto.share.response.ShareCalendarResponse;
import com.example.calendar.repository.calendar.CalendarRepository;
import com.example.calendar.repository.mapping.UserCalendarMpngQueryDslRepository;
import com.example.calendar.repository.mapping.UserCalendarMpngRepository;
import com.example.calendar.repository.user.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@Transactional
@Slf4j
@SpringBootTest
@NoArgsConstructor
public class ShareServiceTest {
    @Autowired
    private ShareService shareService;

    @Autowired
    private UserCalendarMpngRepository userCalendarMpngRepository;
 @Autowired
    private UserCalendarMpngQueryDslRepository userCalendarMpngQueryDslRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CalendarRepository calendarRepository;

    private User user;
    private Calendar calendar;
    private Calendar calendar2;

    @BeforeEach
    public void create() {
        user = userRepository.save(User.builder()
                .nickname("star")
                .password("pw")
                .email("abc@gmail.com")
                .snsType(SnsType.MINICAL)
                .birthday(LocalDate.of(2023, 1, 26))
                .build());

        calendar = calendarRepository.save(
                Calendar.builder()
                        .color("yellow")
                        .title("shareTest1")
                        .category(CategoryType.TRIP)
                        .description("trip plan")
                        .build());

        calendar2 = calendarRepository.save(
                Calendar.builder()
                        .color("yellow")
                        .title("shareTest2")
                        .category(CategoryType.WORK)
                        .description("study plan")
                        .build());
    }

    @Test
    @DisplayName("캘린더 공유 정상 동작 테스트")
    void shareServiceTest() {
        List<Long> calendarIds = List.of(calendar.getId(), calendar2.getId());

        // given
        ShareCalendarRequest request = ShareCalendarRequest.builder()
                .receiveUserId(user.getId())
                .calendarIds(calendarIds)
                .build();

        // when
        ShareCalendarResponse response = shareService.shareCalendar(request);

        // then
        assertThat(response.getUserId()).isEqualTo(request.getReceiveUserId());
        List<UserCalendarMpng> calendars = userCalendarMpngQueryDslRepository.findAllByUserId(request.getReceiveUserId());
        List<Long> calIds = new ArrayList<>();
        for (UserCalendarMpng calendar : calendars) {
            Long calendarId = calendar.getCalendarId();
            calIds.add(calendarId);
        }
        assertThat(calIds).containsAll(calendarIds);
    }
}
