package com.example.calendar.controller.calendar;

import com.example.calendar.repository.calendar.CalendarRepository;
import com.example.calendar.service.calendar.CalendarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(CalendarController.class)
class CalendarControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CalendarRepository calendarRepository;

    @MockBean
    private CalendarService calendarService;

    @AfterEach
    public void clear() {
        calendarRepository.deleteAll();
    }

    @Test
    @DisplayName("캘린더 생성 검증 테스트")
    void createCalendarValidTest() throws Exception {
        // given

        MockHttpServletRequestBuilder builder = post("/api/calendar")

                .content("{\"title\": \"테스트제목\", \"color\": \"red\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(builder)
                .andExpect(status().is4xxClientError())
                .andReturn();

        String message = result.getResolvedException().getMessage();
        Assertions.assertThat(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(message)
                .contains("NotBlank", "description", "category");
    }
}

