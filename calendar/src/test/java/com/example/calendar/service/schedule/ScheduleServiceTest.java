package com.example.calendar.service.schedule;

import com.example.calendar.dto.schedule.response.SelectScheduleByIdResponse;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@NoArgsConstructor
public class ScheduleServiceTest {
    @Autowired
    private ScheduleService scheduleService;

    @Test
    @DisplayName("schedule_id로 schedule 조회가 정상 작동한다.")
    void selectScheduleByIdTest() throws Exception {
        //given
        Long scheduleId=1L;
        SelectScheduleByIdResponse expect=SelectScheduleByIdResponse.builder().build();
        //when
        SelectScheduleByIdResponse result=scheduleService.selectScheduleById(scheduleId);
        //then
        assertThat(result.getTitle()).isEqualTo(expect.getTitle());
    }
}
