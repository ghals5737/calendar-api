package com.example.calendar.global.error;

import com.example.calendar.controller.calendar.CalendarController;
import com.example.calendar.global.error.exception.CustomException;
import com.example.calendar.service.calendar.CalendarService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.calendar.global.error.ErrorCode.CALENDAR_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(CalendarController.class)
class GlobalExceptionAdviceTest {


    @Autowired
    MockMvc mvc;

    @MockBean
    CalendarService calendarService;

    @Test
    @DisplayName("Global Exception 정상 동작 테스트")
    void selectCalendarByIdTest() throws Exception {


    }

}