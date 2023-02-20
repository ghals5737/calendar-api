package com.example.calendar.service.share;

import com.example.calendar.domain.mapping.UserCalendarMpng;
import com.example.calendar.repository.mapping.UserCalendarMpngRepository;
import com.example.calendar.service.user.UserService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@NoArgsConstructor
public class ShareServiceTest {
    @Autowired
    private ShareService shareService;

    @Autowired
    private UserCalendarMpngRepository userCalendarMpngRepository;
    @Test
    void shareServiceTest() {
        // given
        ShareCalendarRequest request = ShareCalendarRequest.builder()
                .receiveUserId(receiveUserId)
                .calendarIds(calendarIds)
                .build();

        // when
        ShareCalendarResponse response = shareService.shareCalendar(request);

        // then
        assertThat(response.getUserId()).isEqualTo(request.getReceiveUserId());
        List<UserCalendarMpng> calendars = userCalendarMpngRepository.findByUserId(request.getReceiveUserId());
        List<Long> calIds=new ArrayList<>();
        for (UserCalendarMpng calendar : calendars) {
            Long calendarId = calendar.getCalendarId();
            calIds.add(calendarId);
        }
        assertThat(calIds).containsAll(calendarIds);
    }
}
